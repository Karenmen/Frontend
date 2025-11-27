const mongoose = require('mongoose'); // Importar mongoose

const productoSchema = new mongoose.Schema({
    nombre: { type: String, required: true, trim: true },
    precio: { type: Number, required: true },
    categoria: {
        type: String,
        required: true,
        trim: true,
        enum: [
            'Pozole',
            'Hamburguesas',
            'Postres',
            'Platillos Mexicanos',
            'Bebidas',
            'Hot Dogs'
        ]
    },
    imagen: { type: String, default: '' }
});

module.exports = mongoose.model('Producto', productoSchema);