package com.example.pozoleria

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MetodoPagoDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "metodos_pago.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_TARJETAS = "tarjetas"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_TARJETAS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titular TEXT,
                numero TEXT,
                fecha TEXT,
                cvv TEXT
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TARJETAS")
        onCreate(db)
    }

    // ------------------------------
    // INSERTAR TARJETA
    // ------------------------------
    fun insertarTarjeta(
        titular: String,
        numero: String,
        fecha: String,
        cvv: String
    ): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("titular", titular)
            put("numero", numero)
            put("fecha", fecha)
            put("cvv", cvv)
        }
        return db.insert(TABLE_TARJETAS, null, values)
    }

    // ------------------------------
    // OBTENER TARJETAS
    // ------------------------------
    fun obtenerTarjetas(): List<String> {
        val lista = mutableListOf<String>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT titular, numero FROM $TABLE_TARJETAS",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val titular = cursor.getString(0)
                val numero = cursor.getString(1)
                val ultimos4 = numero.takeLast(4)
                lista.add("$titular •••• $ultimos4")
            } while (cursor.moveToNext())
        }

        cursor.close()
        return lista
    }
}
