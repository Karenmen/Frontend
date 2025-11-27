require('dotenv').config();
const mongoose = require("mongoose");
const Producto = require("./models/Producto");

const MONGO_URI = process.env.MONGO_URI;

async function seed() {
    try {
        await mongoose.connect(MONGO_URI);
        console.log("Conectado a MongoDB");

        await Producto.deleteMany({});
        console.log("Productos eliminados");

        await Producto.insertMany([
            // 🥣 Pozoles
            { nombre: "Pozole Verde", precio: 85, descripcion: "Pozole estilo casero", categoria: "Pozole" },
            { nombre: "Pozole Rojo", precio: 90, descripcion: "Pozole picosito", categoria: "Pozole" },
            { nombre: "Pozole Blanco", precio: 80, descripcion: "Pozole tradicional", categoria: "Pozole" },

            // 🍔 Hamburguesas
            { nombre: "Hamburguesa Sencilla", precio: 45, descripcion: "Carne, lechuga y jitomate", categoria: "Hamburguesas" },
            { nombre: "Hamburguesa Doble", precio: 60, descripcion: "Doble carne, lechuga y jitomate", categoria: "Hamburguesas" },
            { nombre: "Hamburguesa Hawaiana", precio: 70, descripcion: "Con piña y jamón", categoria: "Hamburguesas" },

            // 🍰 Postres
            { nombre: "Carlota de Limón", precio: 30, descripcion: "Postre frío de limón", categoria: "Postres" },
            { nombre: "Flan", precio: 25, descripcion: "Flan casero", categoria: "Postres" },
            { nombre: "Fresas con Crema", precio: 35, descripcion: "Fresas frescas con crema", categoria: "Postres" },
            { nombre: "Carlota de Capuchino", precio: 35, descripcion: "Postre frío sabor capuchino", categoria: "Postres" },

            // 🇲🇽 Platillos Mexicanos
            { nombre: "Pambazo de Pollo", precio: 40, descripcion: "Pambazo relleno de pollo", categoria: "Platillos Mexicanos" },
            { nombre: "Pambazo de Papa", precio: 35, descripcion: "Pambazo tradicional de papa", categoria: "Platillos Mexicanos" },
            { nombre: "Gordita de Chicharrón", precio: 25, descripcion: "Gordita rellena de chicharrón", categoria: "Platillos Mexicanos" },
            { nombre: "Quesadilla de Pollo", precio: 30, descripcion: "Quesadilla con pollo", categoria: "Platillos Mexicanos" },
            { nombre: "Quesadilla de Queso", precio: 20, descripcion: "Queso derretido en tortilla", categoria: "Platillos Mexicanos" },
            { nombre: "Quesadilla de Champiñones", precio: 25, descripcion: "Queso con champiñones", categoria: "Platillos Mexicanos" },
            { nombre: "Tostada de Tinga", precio: 30, descripcion: "Tostada con tinga de pollo", categoria: "Platillos Mexicanos" },
            { nombre: "Tostada de Pata", precio: 35, descripcion: "Tostada tradicional de pata", categoria: "Platillos Mexicanos" },

            // 🥤 Bebidas
            { nombre: "Agua de Horchata", precio: 20, descripcion: "Agua fresca de horchata", categoria: "Bebidas" },
            { nombre: "Agua de Jamaica", precio: 20, descripcion: "Agua refrescante de jamaica", categoria: "Bebidas" },
            { nombre: "Coca-Cola", precio: 18, descripcion: "Refresco de cola", categoria: "Bebidas" },
            { nombre: "Mundet", precio: 18, descripcion: "Refresco sabor manzana", categoria: "Bebidas" },

            // 🌭 Hot Dogs
            { nombre: "Hot Dog", precio: 25, descripcion: "Hot dog clásico", categoria: "Hot Dogs" }
        ]);

        console.log("Productos insertados correctamente");
        process.exit();
    } catch (error) {
        console.error("Error en seed:", error);
        process.exit(1);
    }
}

seed();