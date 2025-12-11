package com.example.pozoleria

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
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

    private lateinit var txtTiempo: TextView
    private lateinit var txtDistancia: TextView

    private val destinoPozoleria = LatLng(19.2709726, -98.8949347)

    companion object {
        private const val REQUEST_LOCATION = 1001
        private const val DIRECTIONS_API_KEY = "AIzaSyBOWahxcpmHi_ncArUsntIfKs-1c18q3aM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruta_pozoleria)

        txtTiempo = findViewById(R.id.txtTiempo)
        txtDistancia = findViewById(R.id.txtDistancia)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        // 🚦 Tráfico en tiempo real
        mMap.isTrafficEnabled = true

        // Marcador de destino
        mMap.addMarker(
            MarkerOptions()
                .position(destinoPozoleria)
                .title("Pozolería La Carpita")
        )

        verificarPermisosUbicacion()
    }

    private fun verificarPermisosUbicacion() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else iniciarUbicacion()
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
        ) iniciarUbicacion()
    }

    private fun iniciarUbicacion() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        mMap.isMyLocationEnabled = true

        val locationRequest = LocationRequest.create().apply {
            interval = 5000
            fastestInterval = 2000
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation ?: return
                lastLocation = location

                val origen = LatLng(location.latitude, location.longitude)

                // Mover cámara
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origen, 16f))

                // Pedir ruta
                solicitarRuta(origen)
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback!!,
            Looper.getMainLooper()
        )
    }

    private fun solicitarRuta(origen: LatLng) {

        val url =
            "https://maps.googleapis.com/maps/api/directions/json?" +
                    "origin=${origen.latitude},${origen.longitude}" +
                    "&destination=${destinoPozoleria.latitude},${destinoPozoleria.longitude}" +
                    "&mode=driving" +
                    "&departure_time=now" +   // tráfico en tiempo real
                    "&key=$DIRECTIONS_API_KEY"

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                val body = response.body?.string() ?: return@launch

                val json = JSONObject(body)
                val status = json.optString("status")


                println("DIRECTIONS STATUS: $status")
                println("DIRECTIONS JSON: $body")

                if (status != "OK") return@launch

                val route = json.getJSONArray("routes").getJSONObject(0)
                val polyString = route.getJSONObject("overview_polyline").getString("points")
                val points = decodePolyline(polyString)

                val leg = route.getJSONArray("legs").getJSONObject(0)

                val distance = leg.getJSONObject("distance").getString("text")

                // 📌 duration_in_traffic NO siempre viene -> evitamos fallos
                val duration = leg.optJSONObject("duration_in_traffic")?.getString("text")
                    ?: leg.getJSONObject("duration").getString("text")

                withContext(Dispatchers.Main) {
                    actualizarRuta(points, distance, duration)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun actualizarRuta(puntos: List<LatLng>, distance: String, duration: String) {
        rutaPolyline?.remove()

        rutaPolyline = mMap.addPolyline(
            PolylineOptions()
                .addAll(puntos)
                .width(16f)
                .color(Color.parseColor("#E27914")) // naranja
                .geodesic(true)
        )

        txtTiempo.text = "Tiempo aprox (con tráfico): $duration"
        txtDistancia.text = "Distancia: $distance"
    }

    private fun decodePolyline(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        var lat = 0
        var lng = 0

        while (index < encoded.length) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)

            val dlat = if (result and 1 != 0) (result shr 1).inv() else (result shr 1)
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)

            val dlng = if (result and 1 != 0) (result shr 1).inv() else (result shr 1)
            lng += dlng

            poly.add(LatLng(lat / 1E5, lng / 1E5))
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
