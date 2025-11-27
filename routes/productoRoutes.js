const express = require('express');
const router = express.Router();
const Producto = require('../models/Producto');

// 🔹 Función para normalizar texto: quitar acentos, espacios extra y pasar a minúsculas
function normalizar(texto) {
    return texto
        .trim() // quitar espacios al inicio y final
        .toLowerCase() // pasar a minúsculas
        .normalize("NFD") // descomponer caracteres acentuados
        .replace(/[\u0300-\u036f]/g, "") // quitar acentos
        .replace(/\s+/g, " "); // normalizar espacios
}

// 🔹 Obtener productos por categoría (flexible)
router.get('/categoria/:categoria', async(req, res) => {
    try {
        const categoriaRecibida = normalizar(req.params.categoria);

        // Traer todos los productos
        const productos = await Producto.find();

        // Filtrar en memoria usando normalización
        const filtrados = productos.filter(prod => normalizar(prod.categoria) === categoriaRecibida);

        console.log("Categoría recibida:", req.params.categoria, "→ normalizada:", categoriaRecibida);
        filtrados.forEach(p => console.log(`Producto: ${p.nombre} → categoría DB normalizada: ${normalizar(p.categoria)}`));
        console.log("Filtrados:", filtrados.length);

        // Si no se encuentra nada, avisar
        if (filtrados.length === 0) {
            return res.status(404).json({ message: "No se encontraron productos en esta categoría" });
        }

        res.json(filtrados);
    } catch (err) {
        console.error("Error al buscar productos por categoría:", err.message);
        res.status(500).json({ error: err.message });
    }
});

module.exports = router;