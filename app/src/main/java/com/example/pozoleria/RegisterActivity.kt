package com.example.pozoleria

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pozoleria.databinding.FormularioBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: FormularioBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegistrarUsuario.setOnClickListener {

            val nombre = binding.edtNombre.text.toString().trim()
            val correo = binding.edtEmailRegister.text.toString().trim()
            val password = binding.edtPasswordRegister.text.toString().trim()
            val confirmar = binding.edtConfirmPassword.text.toString().trim()

            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(this, "Correo inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmar) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            crearCuentaFirebase(nombre, correo, password)
        }

        binding.txtIrLogin.setOnClickListener {
            finish()
        }
    }

    private fun crearCuentaFirebase(nombre: String, correo: String, password: String) {

        auth.createUserWithEmailAndPassword(correo, password)
            .addOnSuccessListener {

                auth.currentUser?.sendEmailVerification()
                    ?.addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Cuenta creada 🎉\nTe enviamos un correo para verificar tu cuenta.",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    }
                    ?.addOnFailureListener {
                        Toast.makeText(this, "Error al enviar verificación: ${it.message}", Toast.LENGTH_LONG).show()
                    }

            }
            .addOnFailureListener {
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }
}
