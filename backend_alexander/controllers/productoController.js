const Producto = require('../models/producto');

exports.obtenerProductos = async (req, res) => {
  const productos = await Producto.find();
  res.json(productos);
};

exports.crearProducto = async (req, res) => {
  const nuevo = new Producto(req.body);
  await nuevo.save();
  res.json({ mensaje: 'Producto agregado correctamente' });
};
