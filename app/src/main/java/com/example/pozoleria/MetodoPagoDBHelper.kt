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

        // TABLA
        private const val TABLE_TARJETAS = "tarjetas"

        // COLUMNAS
        private const val COL_ID = "id"
        private const val COL_TITULAR = "titular"
        private const val COL_NUMERO = "numero"
        private const val COL_FECHA = "fecha"
        private const val COL_CVV = "cvv"
    }

    // =============================
    // CREACIÓN DE BASE DE DATOS
    // =============================
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_TARJETAS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TITULAR TEXT NOT NULL,
                $COL_NUMERO TEXT NOT NULL,
                $COL_FECHA TEXT NOT NULL,
                $COL_CVV TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TARJETAS")
        onCreate(db)
    }

    // =============================
    // INSERTAR TARJETA
    // =============================
    fun insertarTarjeta(
        titular: String,
        numero: String,
        fecha: String,
        cvv: String
    ): Long {

        val db = writableDatabase

        val values = ContentValues().apply {
            put(COL_TITULAR, titular)
            put(COL_NUMERO, numero)
            put(COL_FECHA, fecha)
            put(COL_CVV, cvv)
        }

        val result = db.insert(TABLE_TARJETAS, null, values)
        db.close()
        return result
    }

    // =============================
    // OBTENER TARJETAS (KOTLIN)
    // =============================
    fun obtenerTarjetas(): List<String> {
        val lista = mutableListOf<String>()
        val db = readableDatabase

        val cursor = db.query(
            TABLE_TARJETAS,
            arrayOf(COL_TITULAR, COL_NUMERO),
            null,
            null,
            null,
            null,
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
        db.close()
        return lista
    }

    // =============================
    // OBTENER TARJETAS (JAVA FRIENDLY)
    // 👉 ESTE ERA EL QUE FALTABA
    // =============================
    fun obtenerTarjetasSimple(): ArrayList<String> {
        val lista = ArrayList<String>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT $COL_TITULAR, $COL_NUMERO FROM $TABLE_TARJETAS",
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
        db.close()
        return lista
    }
}
