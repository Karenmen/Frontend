package com.example.pozoleria.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pozoleria.R
import com.example.pozoleria.MetodoPagoDBHelper

class MetodoPagoActivity : AppCompatActivity() {

    private lateinit var db: MetodoPagoDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metodo_pago)

        db = MetodoPagoDBHelper(this)

        val etTitular = findViewById<EditText>(R.id.etTitular)
        val etNumero = findViewById<EditText>(R.id.etNumero)
        val etFecha = findViewById<EditText>(R.id.etFecha)
        val etCvv = findViewById<EditText>(R.id.etCvv)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarTarjeta)

        btnGuardar.setOnClickListener {
            val titular = etTitular.text.toString()
            val numero = etNumero.text.toString()
            val fecha = etFecha.text.toString()
            val cvv = etCvv.text.toString()

            if (titular.isBlank() || numero.isBlank() || fecha.isBlank() || cvv.isBlank()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultado = db.insertarTarjeta(
                titular,
                numero,
                fecha,
                cvv
            )

            if (resultado > 0) {
                Toast.makeText(this, "Tarjeta guardada correctamente 💳", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al guardar tarjeta", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
