const express = require("express");
const router = express.Router();
const productoController = require("../controllers/productoController");

// Obtener todos los productos
router.get("/", productoController.obtenerProductos);

// Obtener productos por categoría
router.get("/categoria/:categoria", productoController.obtenerPorCategoria);

// Crear producto
router.post("/", productoController.crearProducto);

module.exports = router;
