const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
require('dotenv').config();

const productoRoutes = require('./routes/productoRoutes');
const usuarioRoutes = require('./routes/usuarioRoutes');

const app = express();

// ====== Middleware ======
app.use(cors({
    origin: "*",
    methods: "GET,POST,PUT,DELETE",
    allowedHeaders: "Content-Type, Authorization"
}));

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// ⭐ Servir imágenes estáticas
app.use('/imagenes', express.static('imagenes'));

// Logger simple
app.use((req, res, next) => {
  console.log(`📡 ${req.method} → ${req.url}`);
  next();
});

// Ruta de prueba
app.get('/api/ping', (req, res) => {
  res.json({ message: 'Backend OK 👍 Conexión correcta.' });
});

// Rutas API
app.use('/api/productos', productoRoutes);
app.use('/api/usuarios', usuarioRoutes);

// ====== CONEXIÓN A MONGODB ======
mongoose.connect(process.env.MONGO_URI)
  .then(() => {
    console.log('✅ Conectado a MongoDB Atlas');

    const PORT = process.env.PORT || 3000;

    // ⭐ En Render SIEMPRE usa '0.0.0.0'
    app.listen(PORT, '0.0.0.0', () => {
      console.log(`🚀 Servidor iniciado en puerto ${PORT}`);
    });
  })
  .catch(err => {
    console.error('❌ Error al conectar con MongoDB:', err.message);
  });
