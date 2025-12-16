package com.example.pozoleria

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pozoleria.models.CartItem

class VentaDBHelper(context: Context) :
    SQLiteOpenHelper(context, "ventas.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE pedidos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                total REAL,
                fecha TEXT
            )
        """)

        db.execSQL("""
            CREATE TABLE detalle_pedido (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                pedido_id INTEGER,
                nombre TEXT,
                precio REAL,
                cantidad INTEGER,
                FOREIGN KEY(pedido_id) REFERENCES pedidos(id)
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS detalle_pedido")
        db.execSQL("DROP TABLE IF EXISTS pedidos")
        onCreate(db)
    }

    // GUARDAR PEDIDO COMPLETO

    fun guardarPedido(items: List<CartItem>, total: Double) {

        val db = writableDatabase

        // Insertar pedido
        val pedidoValues = ContentValues().apply {
            put("total", total)
            put("fecha", System.currentTimeMillis().toString())
        }

        val pedidoId = db.insert("pedidos", null, pedidoValues)

        // Insertar detalle del pedido
        items.forEach { item ->
            val detalleValues = ContentValues().apply {
                put("pedido_id", pedidoId)
                put("nombre", item.name)
                put("precio", item.price)
                put("cantidad", item.quantity)
            }
            db.insert("detalle_pedido", null, detalleValues)
        }
    }
}
