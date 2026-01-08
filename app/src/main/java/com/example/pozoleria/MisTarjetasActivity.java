package com.example.pozoleria;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MisTarjetasActivity extends AppCompatActivity {

    private ListView listTarjetas;
    private MetodoPagoDBHelper db; // ✅ YA DECLARADO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_tarjetas);

        listTarjetas = findViewById(R.id.listTarjetas);

        // ✅ INICIALIZAR DB
        db = new MetodoPagoDBHelper(this);

        // ✅ OBTENER TARJETAS (JAVA FRIENDLY)
        ArrayList<String> tarjetas = db.obtenerTarjetasSimple();

        // ✅ ADAPTER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_single_choice,
                tarjetas
        );

        listTarjetas.setAdapter(adapter);
        listTarjetas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
