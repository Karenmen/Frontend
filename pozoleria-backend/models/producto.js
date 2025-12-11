const mongoose = require('mongoose');

const productoSchema = new mongoose.Schema({
    nombre: String,
    precio: Number,
    categoria: String,
    imagen: {
        type: String,
        default: ""
    }
});

module.exports = mongoose.model('Producto', productoSchema);
