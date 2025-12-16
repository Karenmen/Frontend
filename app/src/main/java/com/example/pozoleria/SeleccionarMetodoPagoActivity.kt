package com.example.pozoleria.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.pozoleria.HomeActivity
import com.example.pozoleria.R
import com.example.pozoleria.MetodoPagoDBHelper

class SeleccionarMetodoPagoActivity : AppCompatActivity() {

    private lateinit var db: MetodoPagoDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccionar_metodo_pago)

        db = MetodoPagoDBHelper(this)

        val listTarjetas = findViewById<ListView>(R.id.listTarjetas)
        val btnAgregar = findViewById<Button>(R.id.btnAgregarMetodo)
        val btnPagar = findViewById<Button>(R.id.btnConfirmarPago)

        // Cargar tarjetas
        val tarjetas = db.obtenerTarjetas()
        listTarjetas.adapter = android.widget.ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice,
            tarjetas
        )
        listTarjetas.choiceMode = ListView.CHOICE_MODE_SINGLE

        // Agregar método de pago
        btnAgregar.setOnClickListener {
            startActivity(Intent(this, MetodoPagoActivity::class.java))
        }

        // Pagar (SIMULADO)
        btnPagar.setOnClickListener {
            mostrarDialogPagoExitoso()
        }
    }

    override fun onResume() {
        super.onResume()
        val listTarjetas = findViewById<ListView>(R.id.listTarjetas)
        val tarjetas = db.obtenerTarjetas()
        listTarjetas.adapter = android.widget.ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice,
            tarjetas
        )
    }

    private fun mostrarDialogPagoExitoso() {
        AlertDialog.Builder(this)
            .setTitle("Pago exitoso")
            .setMessage("Tu pago se realizó correctamente.")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { _, _ ->
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            .show()
    }
}
