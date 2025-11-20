// models/usuario.js
const mongoose = require('mongoose');

const usuarioSchema = new mongoose.Schema({
  nombre: {
    type: String,
    required: [true, 'El nombre es obligatorio'],
    trim: true
  },
  correo: {
    type: String,
    required: [true, 'El correo es obligatorio'],
    unique: true,
    lowercase: true,
    match: [/^[^\s@]+@[^\s@]+\.[^\s@]+$/, 'Correo inválido']
  },
  password: {
    type: String,
    required: [true, 'La contraseña es obligatoria'],
    minlength: [4, 'La contraseña debe tener al menos 4 caracteres']
  }
}, {
  timestamps: true // agrega createdAt y updatedAt automáticamente
});

module.exports = mongoose.model('Usuario', usuarioSchema);
