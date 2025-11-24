package com.example.pozoleria

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MenuPrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            // Carga el layout principal
            setContentView(R.layout.activity_menu_principal)

            // Mensaje al ingresar
            Toast.makeText(this, "Bienvenido al menú principal 👋", Toast.LENGTH_SHORT).show()

            // Aquí puedes agregar navegación, recycler, etc.
        } catch (e: Exception) {
            Toast.makeText(this, "Error al cargar el menú: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}
