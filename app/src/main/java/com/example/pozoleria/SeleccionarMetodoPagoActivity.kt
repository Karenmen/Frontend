package com.example.pozoleria.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pozoleria.HomeActivity
import com.example.pozoleria.MetodoPagoDBHelper
import com.example.pozoleria.R
import com.example.pozoleria.VentaDBHelper
import com.example.pozoleria.models.CartStorage

class SeleccionarMetodoPagoActivity : AppCompatActivity() {

    private lateinit var metodoPagoDB: MetodoPagoDBHelper
    private lateinit var ventaDB: VentaDBHelper
    private lateinit var listTarjetas: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccionar_metodo_pago)

        metodoPagoDB = MetodoPagoDBHelper(this)
        ventaDB = VentaDBHelper(this)

        listTarjetas = findViewById(R.id.listTarjetas)
        val btnAgregar = findViewById<Button>(R.id.btnAgregarMetodo)
        val btnPagar = findViewById<Button>(R.id.btnConfirmarPago)

        // Configuración del ListView
        listTarjetas.choiceMode = ListView.CHOICE_MODE_SINGLE
        cargarTarjetas()

        // ➕ Agregar método de pago
        btnAgregar.setOnClickListener {
            startActivity(Intent(this, MetodoPagoActivity::class.java))
        }

        // 💳 Confirmar pago
        btnPagar.setOnClickListener {

            val posicionSeleccionada = listTarjetas.checkedItemPosition

            if (posicionSeleccionada == ListView.INVALID_POSITION) {
                Toast.makeText(
                    this,
                    "Selecciona un método de pago",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // 🚨 Validar que haya productos
            if (CartStorage.getItems().isEmpty()) {
                Toast.makeText(
                    this,
                    "El carrito está vacío",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // ✅ GUARDAR VENTA EN SQLITE
            ventaDB.guardarPedido(
                CartStorage.getItems(),
                CartStorage.getTotal()
            )

            // 🧹 Limpiar carrito
            CartStorage.clear()

            mostrarDialogPagoExitoso()
        }
    }

    override fun onResume() {
        super.onResume()
        cargarTarjetas()
    }

    // =============================
    // CARGAR TARJETAS DESDE SQLITE
    // =============================
    private fun cargarTarjetas() {
        val tarjetas = metodoPagoDB.obtenerTarjetas()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice,
            tarjetas
        )
        listTarjetas.adapter = adapter
    }

    // DIÁLOGO DE PAGO EXITOSO
    private fun mostrarDialogPagoExitoso() {
        AlertDialog.Builder(this)
            .setTitle("Pago exitoso")
            .setMessage("Tu pago se realizó correctamente.")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { _, _ ->
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            .show()
    }
}
