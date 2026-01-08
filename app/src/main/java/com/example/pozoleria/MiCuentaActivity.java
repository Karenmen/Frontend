package com.example.pozoleria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MiCuentaActivity extends AppCompatActivity {

    private TextView txtNombreUsuario, txtCorreoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta);

        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtCorreoUsuario = findViewById(R.id.txtCorreoUsuario);

        SessionManager session = new SessionManager(this);

        String nombre = session.getNombre();
        String email = session.getEmail();

        txtNombreUsuario.setText(nombre != null && !nombre.isEmpty() ? nombre : "Usuario");
        txtCorreoUsuario.setText(email != null && !email.isEmpty() ? email : "correo@ejemplo.com");

        // OPCIONES
        View opcionNombre = findViewById(R.id.opcionNombre);
        View opcionFecha = findViewById(R.id.opcionFecha);
        View opcionTelefono = findViewById(R.id.opcionTelefono);
        View opcionDirecciones = findViewById(R.id.opcionDirecciones);
        View opcionTarjetas = findViewById(R.id.opcionTarjetas);

        ((TextView) opcionNombre.findViewById(R.id.txtOpcion))
                .setText("Nombre y Apellido");

        ((TextView) opcionFecha.findViewById(R.id.txtOpcion))
                .setText("Fecha de nacimiento");

        ((TextView) opcionTelefono.findViewById(R.id.txtOpcion))
                .setText("Número de teléfono");

        ((TextView) opcionDirecciones.findViewById(R.id.txtOpcion))
                .setText("Direcciones de entrega");

        ((TextView) opcionTarjetas.findViewById(R.id.txtOpcion))
                .setText("Mis tarjetas");

        // CLICK REAL
        opcionTarjetas.setOnClickListener(v ->
                startActivity(new Intent(this, MisTarjetasActivity.class))
        );
    }
}
