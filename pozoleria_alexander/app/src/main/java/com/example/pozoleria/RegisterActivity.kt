package com.example.pozoleria

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pozoleria.databinding.FormularioBinding
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: FormularioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🟢 Acción del botón "Registrar usuario"
        binding.btnRegistrarUsuario.setOnClickListener {
            val nombre = binding.edtNombre.text.toString().trim()
            val correo = binding.edtEmailRegister.text.toString().trim()
            val password = binding.edtPasswordRegister.text.toString().trim()
            val confirmar = binding.edtConfirmPassword.text.toString().trim()

            when {
                nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || confirmar.isEmpty() -> {
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
                password != confirmar -> {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    registrarUsuario(nombre, correo, password)
                }
            }
        }

        // 🔙 Volver al login
        binding.txtIrLogin.setOnClickListener { finish() }
    }

    // 🔹 Conexión al backend
    private fun registrarUsuario(nombre: String, correo: String, password: String) {
        // ✅ IP correcta para emulador
        val url = "http://10.0.2.2:3000/api/usuarios/registro"

        val params = JSONObject().apply {
            put("nombre", nombre)
            put("correo", correo)
            put("password", password)
        }

        val request = object : JsonObjectRequest(
            Request.Method.POST, url, params,
            { response ->
                val mensaje = response.optString("message", "Usuario registrado correctamente ✅")
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                finish() // Volver al login tras éxito
            },
            { error ->
                val code = error.networkResponse?.statusCode
                val body = error.networkResponse?.data?.toString(Charsets.UTF_8)
                val mensaje = when {
                    code != null -> "Error HTTP $code: $body"
                    else -> "❌ No se pudo conectar con el servidor"
                }
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return hashMapOf("Content-Type" to "application/json; charset=utf-8")
            }
        }

        // ⏱ Política de reintento
        request.retryPolicy = DefaultRetryPolicy(
            8000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        Volley.newRequestQueue(this).add(request)
    }
}
