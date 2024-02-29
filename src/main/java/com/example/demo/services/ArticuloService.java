package com.example.demo.services;

import java.util.Optional;

import com.example.demo.modelo.Articulo;

public interface ArticuloService {
	
	public Optional<Articulo> findById(String codigo);

    public Iterable<Articulo> findAll();

    public Articulo save(Articulo articulo);

    public void delete(Articulo articulo);

    public void deleteById(String codigo);
}
