package com.example.demo.services;

import java.util.Optional;

import com.example.demo.modelo.Articulo;
import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.Usuario;
import com.example.demo.modelo.Venta;

public interface CarritoService {
	public Optional<Carrito> findById(Integer id);

    public Iterable<Carrito> findAll();

    public Carrito save(Carrito carrito);

    public void delete(Carrito carrito);

    public void deleteById(Integer id);
    
    public void abrir(Usuario usuario,Carrito carrito);
    
    public void agregarArticulo(Articulo articulo, Carrito carrito, Integer cantidad);
    
    public void vaciarCarrito(Carrito carrito);
    
    public Venta cerrar(Carrito carrito);
}


