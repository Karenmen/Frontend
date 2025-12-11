const Producto = require("../models/producto");

// Obtener todos los productos
exports.obtenerProductos = async (req, res) => {
    try {
        const productos = await Producto.find();
        res.json(productos);
    } catch (error) {
        res.status(500).json({
            message: "Error al obtener productos",
            error
        });
    }
};

// Obtener productos por categoría
exports.obtenerPorCategoria = async (req, res) => {
    try {
        let categoria = req.params.categoria.trim().toLowerCase().replace(/\s+/g, "");

        console.log("📌 Categoria recibida:", categoria);

        const productos = await Producto.find({
            categoria: {
                $regex: new RegExp(`^${categoria}$`, "i")
            }
        });

        console.log("📌 Productos encontrados:", productos.length);

        res.json(productos);

    } catch (error) {
        console.error("❌ Error en obtenerPorCategoria:", error);
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
        res.status(500).json({
            message: "Error al crear producto",
            error
        });
    }
};
