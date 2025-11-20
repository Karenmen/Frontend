// routes/usuarioRoutes.js
const express = require('express');
const router = express.Router();
const Usuario = require('../models/usuario');

// 🔹 Ruta de prueba
router.get('/', (req, res) => {
  res.json({ message: '✅ Ruta /api/usuarios funcionando correctamente' });
});

// 🟢 REGISTRO DE USUARIO
router.post('/registro', async (req, res) => {
  try {
    console.log('📩 Datos recibidos en registro:', req.body);

    // Validar cuerpo vacío
    if (!req.body || Object.keys(req.body).length === 0) {
      return res.status(400).json({ message: 'Cuerpo de la solicitud vacío ❌' });
    }

    const { nombre, correo, password } = req.body;

    if (!nombre || !correo || !password) {
      return res.status(400).json({ message: 'Todos los campos son obligatorios' });
    }

    const existe = await Usuario.findOne({ correo });
    if (existe) {
      return res.status(400).json({ message: 'El correo ya está registrado' });
    }

    const nuevoUsuario = new Usuario({ nombre, correo, password });
    await nuevoUsuario.save();

    console.log('✅ Usuario registrado:', nuevoUsuario.correo);
    res.status(201).json({
      message: 'Usuario registrado correctamente ✅',
      usuario: {
        id: nuevoUsuario._id,
        nombre: nuevoUsuario.nombre,
        correo: nuevoUsuario.correo
      }
    });
  } catch (error) {
    console.error('❌ Error en registro:', error.message);
    res.status(500).json({ message: 'Error al registrar usuario', error: error.message });
  }
});

// 🟢 LOGIN DE USUARIO
router.post('/login', async (req, res) => {
  try {
    console.log('📩 Intento de login:', req.body);

    // Validar cuerpo vacío
    if (!req.body || Object.keys(req.body).length === 0) {
      return res.status(400).json({ message: 'Cuerpo de la solicitud vacío ❌' });
    }

    const { correo, password } = req.body;

    if (!correo || !password) {
      return res.status(400).json({ message: 'Faltan campos' });
    }

    const usuario = await Usuario.findOne({ correo });
    if (!usuario) {
      console.log('❌ Usuario no encontrado:', correo);
      return res.status(404).json({ message: 'Usuario no encontrado' });
    }

    if (usuario.password !== password) {
      console.log('⚠️ Contraseña incorrecta para:', correo);
      return res.status(401).json({ message: 'Contraseña incorrecta' });
    }

    console.log('✅ Login exitoso para:', usuario.correo);
    res.json({
      message: 'Inicio de sesión exitoso ✅',
      usuario: {
        id: usuario._id,
        nombre: usuario.nombre,
        correo: usuario.correo
      }
    });
  } catch (error) {
    console.error('🔥 Error en login:', error.message);
    res.status(500).json({ message: 'Error al iniciar sesión', error: error.message });
  }
});

module.exports = router;
