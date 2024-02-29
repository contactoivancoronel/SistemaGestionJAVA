package com.example.demo.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.modelo.Articulo;
import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.Usuario;
import com.example.demo.modelo.Venta;
import com.example.demo.repositiries.CarritoRepository;

@Service
public class CarritoServiceImplementado implements CarritoService {
	@Autowired
    private CarritoRepository articulorepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Carrito> findById(Integer id) { return articulorepository.findById(id); }


    @Override
    @Transactional(readOnly = true)
    public Iterable<Carrito> findAll() { return articulorepository.findAll(); }

    @Override
    @Transactional
    public Carrito save(Carrito carrito) { return articulorepository.save(carrito); }

    @Override
    @Transactional
    public void delete(Carrito carrito) { articulorepository.delete(carrito); }

    @Override
    @Transactional
    public void deleteById(Integer id) { articulorepository.deleteById(id); }
    
    @Override
    @Transactional
    public void abrir(Usuario usuario,Carrito carrito){
    	carrito.abrir(usuario);
    	}
    
    @Override
    @Transactional
    public void agregarArticulo(Articulo articulo, Carrito carrito, Integer cantidad){
    	carrito.agregarArticulo(articulo, cantidad);
    	}
    
    @Override
    @Transactional
	public void vaciarCarrito(Carrito carrito){
    	carrito.vaciarCarrito();
    	}
    
    @Override
    @Transactional
	public Venta cerrar(Carrito carrito) {
    	return carrito.cerrar();
    	}
}