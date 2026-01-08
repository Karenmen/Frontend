package com.example.pozoleria

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pozoleria.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        session = SessionManager(this)

        if (session.isLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (!validarCampos(email, password)) return@setOnClickListener
            iniciarSesionFirebase(email, password)
        }

        binding.txtRegistrar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validarCampos(email: String, password: String): Boolean {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Ingresa un correo válido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Ingresa una contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun iniciarSesionFirebase(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                val user = auth.currentUser

                if (user != null && user.isEmailVerified) {

                    val nombre = user.displayName ?: email.substringBefore("@")

                    // ✅ AQUÍ YA NO HAY ERROR
                    session.saveSession(
                        nombre = nombre,
                        email = email,
                        uid = user.uid
                    )

                    Toast.makeText(this, "Bienvenido $nombre 👋", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()

                } else {
                    Toast.makeText(
                        this,
                        "Debes verificar tu correo antes de iniciar sesión.",
                        Toast.LENGTH_LONG
                    ).show()
                    FirebaseAuth.getInstance().signOut()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Correo o contraseña incorrectos ❌", Toast.LENGTH_SHORT).show()
            }
    }
}
