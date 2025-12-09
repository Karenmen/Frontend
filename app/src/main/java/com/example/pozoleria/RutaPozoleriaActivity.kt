package com.example.pozoleria

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import android.graphics.Color

class RutaPozoleriaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationCallback: LocationCallback? = null
    private var lastLocation: Location? = null

    private var rutaPolyline: Polyline? = null
    private var modoActual: String = "driving" // driving | walking | transit

    private lateinit var txtTiempo: TextView
    private lateinit var txtDistancia: TextView

    // Coordenadas fijas de la pozolería
    private val destinoPozoleria = LatLng(19.2709726, -98.8949347)

    companion object {
        private const val REQUEST_LOCATION = 1001
        private const val DIRECTIONS_API_KEY = "AIzaSyBOWahxcpmHi_ncArUsntIfKs-1c18q3aM" // <- opcionalmente puedes usar la misma del manifest
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruta_pozoleria)

        txtTiempo = findViewById(R.id.txtTiempo)
        txtDistancia = findViewById(R.id.txtDistancia)

        val btnAuto: Button = findViewById(R.id.btnAuto)
        val btnCaminando: Button = findViewById(R.id.btnCaminando)
        val btnTransporte: Button = findViewById(R.id.btnTransporte)

        btnAuto.setOnClickListener {
            modoActual = "driving"
            recalcularRutaSiHayUbicacion()
        }

        btnCaminando.setOnClickListener {
            modoActual = "walking"
            recalcularRutaSiHayUbicacion()
        }

        btnTransporte.setOnClickListener {
            modoActual = "transit"
            recalcularRutaSiHayUbicacion()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        // Marcador de la pozolería
        mMap.addMarker(
            MarkerOptions()
                .position(destinoPozoleria)
                .title("Pozolería")
        )

        verificarPermisosUbicacion()
    }

    private fun verificarPermisosUbicacion() {
        val permisoFine = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permisoFine != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            iniciarActualizacionesUbicacion()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_LOCATION &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            iniciarActualizacionesUbicacion()
        }
    }

    private fun iniciarActualizacionesUbicacion() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        mMap.isMyLocationEnabled = true

        val locationRequest = LocationRequest.create().apply {
            interval = 5000           // cada 5 segundos
            fastestInterval = 2000    // mínimo 2 segundos
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation ?: return
                lastLocation = location

                val origen = LatLng(location.latitude, location.longitude)

                // Cámara siguiendo al usuario (tipo Waze/Google Maps)
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(origen, 15f)
                )

                // Calcular ruta en cada actualización (puedes optimizar más adelante)
                solicitarRuta(origen, destinoPozoleria, modoActual)
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback as LocationCallback,
            Looper.getMainLooper()
        )
    }

    private fun recalcularRutaSiHayUbicacion() {
        val loc = lastLocation ?: return
        val origen = LatLng(loc.latitude, loc.longitude)
        solicitarRuta(origen, destinoPozoleria, modoActual)
    }

    private fun solicitarRuta(origen: LatLng, destino: LatLng, mode: String) {
        val url = buildDirectionsUrl(origen, destino, mode)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(url)
                    .build()

                val response = client.newCall(request).execute()
                val body = response.body?.string() ?: return@launch

                val json = JSONObject(body)
                val status = json.optString("status")
                if (status != "OK") return@launch

                val routes = json.getJSONArray("routes")
                if (routes.length() == 0) return@launch

                val route = routes.getJSONObject(0)
                val overviewPolyline =
                    route.getJSONObject("overview_polyline").getString("points")

                val legs = route.getJSONArray("legs")
                val leg = legs.getJSONObject(0)
                val distanceText = leg.getJSONObject("distance").getString("text")
                val durationText = leg.getJSONObject("duration").getString("text")

                val points = decodePolyline(overviewPolyline)

                withContext(Dispatchers.Main) {
                    actualizarRutaEnMapa(points, distanceText, durationText)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun buildDirectionsUrl(origen: LatLng, destino: LatLng, mode: String): String {
        val originParam = "${origen.latitude},${origen.longitude}"
        val destParam = "${destino.latitude},${destino.longitude}"

        return "https://maps.googleapis.com/maps/api/directions/json" +
                "?origin=$originParam" +
                "&destination=$destParam" +
                "&mode=$mode" +
                "&key=$DIRECTIONS_API_KEY"
    }

    private fun actualizarRutaEnMapa(
        puntos: List<LatLng>,
        distance: String,
        duration: String
    ) {
        // Borrar ruta anterior
        rutaPolyline?.remove()

        rutaPolyline = mMap.addPolyline(
            PolylineOptions()
                .addAll(puntos)
                .width(12f)
                .color(Color.BLUE)
        )

        txtTiempo.text = "Tiempo aprox: $duration"
        txtDistancia.text = "Distancia: $distance"
    }

    // Decodificar polyline (formato de Google Directions)
    private fun decodePolyline(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if ((result and 1) != 0) (result shr 1).inv() else (result shr 1)
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if ((result and 1) != 0) (result shr 1).inv() else (result shr 1)
            lng += dlng

            val latLng = LatLng(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            poly.add(latLng)
        }

        return poly
    }

    override fun onDestroy() {
        super.onDestroy()
        locationCallback?.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
    }
}
