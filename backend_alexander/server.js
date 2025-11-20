// server.js
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
require('dotenv').config();

// Importar rutas
const productoRoutes = require('./routes/productoRoutes');
const usuarioRoutes = require('./routes/usuarioRoutes');

const app = express();

// 🧱 Middleware base
app.use(cors()); // Permitir peticiones desde cualquier origen (emulador, físico, navegador)
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// 🧩 Logger para depuración (ver qué llega desde Android)
app.use((req, res, next) => {
  console.log(`📡 Petición recibida: ${req.method} ${req.url}`);
  next();
});

// 🧠 Ruta de prueba
app.get('/api/ping', (req, res) => {
  res.json({ message: '✅ Conexión exitosa con el backend desde Android!' });
});

// 🧭 Rutas principales
app.use('/api/productos', productoRoutes);
app.use('/api/usuarios', usuarioRoutes);

// 🧩 Conexión a MongoDB Atlas
mongoose.connect(process.env.MONGO_URI)
  .then(() => {
    console.log('✅ Conectado a MongoDB Atlas');

    const PORT = process.env.PORT || 3000;
    // Escuchar en todas las interfaces (permite acceso desde emulador)
    app.listen(PORT, '0.0.0.0', () => {
      console.log(`🚀 Servidor corriendo en http://192.168.1.74:${PORT}`);
      console.log(`📍 Ruta de prueba: http://192.168.1.74:${PORT}/api/ping`);
      console.log(`📍 Rutas de usuario: http://192.168.1.74:${PORT}/api/usuarios`);
    });
  })
  .catch(err => {
    console.error('❌ Error al conectar a MongoDB:', err.message);
  });
