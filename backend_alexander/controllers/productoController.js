const Producto = require('../models/producto');

// Obtener todos los productos
exports.obtenerProductos = async (req, res) => {
    try {
        const productos = await Producto.find();
        res.json(productos);
    } catch (error) {
        res.status(500).json({ message: "Error al obtener productos", error });
    }
};

// Obtener productos por categoría
exports.obtenerPorCategoria = async (req, res) => {
    try {
        const categoria = req.params.categoria;

        const productos = await Producto.find({
            categoria: { $regex: '^' + categoria + '$', $options: 'i' }
        });

        res.json(productos);
    } catch (error) {
        res.status(500).json({
            message: "Error al obtener productos por categoría",
            error
        });
    }
};

// Crear producto
exports.crearProducto = async (req, res) => {
    try {
        const nuevoProducto = new Producto(req.body);
        await nuevoProducto.save();
        res.json({
            message: "Producto creado correctamente",
            producto: nuevoProducto
        });
    } catch (error) {
        res.status(500).json({ message: "Error al crear producto", error });
    }
};
