// server.js
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
require('dotenv').config();

const productoRoutes = require('./routes/productoRoutes');
const usuarioRoutes = require('./routes/usuarioRoutes');

const app = express();

// ====== Middleware ======
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// ⭐ Servir imágenes estáticas desde /imagenes
app.use('/imagenes', express.static('imagenes'));

// Logger simple
app.use((req, res, next) => {
  console.log(`📡 ${req.method} → ${req.url}`);
  next();
});

// Ruta de prueba
app.get('/api/ping', (req, res) => {
  res.json({ message: 'Backend OK 👍 Conexión exitosa desde Android.' });
});

// Rutas API
app.use('/api/productos', productoRoutes);
app.use('/api/usuarios', usuarioRoutes);

// ====== CONEXIÓN A MONGODB ======
mongoose.connect(process.env.MONGO_URI)
  .then(() => {
    console.log('✅ Conectado a MongoDB Atlas');

    const PORT = process.env.PORT || 3000;

    // ⭐ Necesario para Android Emulator
    app.listen(PORT, '0.0.0.0', () => {
      console.log(`🚀 Servidor iniciado en puerto ${PORT}`);
      console.log(`📍 Ruta de prueba: http://localhost:${PORT}/api/ping`);
      console.log(`📍 Productos: http://localhost:${PORT}/api/productos`);
      console.log(`📍 Usuarios:  http://localhost:${PORT}/api/usuarios`);
      console.log(`📸 Imágenes:  http://localhost:${PORT}/imagenes/tu_imagen.jpg`);
    });
  })
  .catch(err => {
    console.error('❌ Error al conectar con MongoDB:', err.message);
  });
