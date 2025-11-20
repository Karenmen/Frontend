package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Configuración visual para pantalla completa
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- AQUÍ EMPIEZA LA MAGIA ---

        // Esperar 3 segundos (3000 milisegundos)
        Handler(Looper.getMainLooper()).postDelayed({

            // 1. Crear el viaje hacia CategoriesActivity
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)

            // 2. Cerrar esta pantalla de carga para que no se pueda volver a ella
            finish()

        }, 3000)
    }
}