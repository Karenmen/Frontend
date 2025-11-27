const Producto = require("../models/producto");

const getProductos = async(req, res) => {
    try {
        const { categoria } = req.query;

        let filtro = {};

        if (categoria) {
            filtro.categoria = categoria;
        }

        const productos = await Producto.find(filtro);
        res.json(productos);

    } catch (error) {
        res.status(500).json({ error: "Error al obtener productos" });
    }
};

const createProducto = async(req, res) => {
    try {
        const nuevo = new Producto(req.body);
        await nuevo.save();
        res.json(nuevo);
    } catch (error) {
        res.status(500).json({ error: "Error al crear producto" });
    }
};

module.exports = { getProductos, createProducto };