package com.example.demo.services;

import java.util.Optional;

import com.example.demo.modelo.Venta;

public interface VentaService {
	
	public Optional<Venta> findById(Integer id);

    public Iterable<Venta> findAll();

    public Venta save(Venta venta);

    public void delete(Venta venta);

    public void deleteById(Integer id);
}
