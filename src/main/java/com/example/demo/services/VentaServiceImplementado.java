package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.modelo.Venta;
import com.example.demo.repositiries.VentaRepository;

@Service
public class VentaServiceImplementado implements VentaService {
	@Autowired
    private VentaRepository ventarepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> findById(Integer id) { return ventarepository.findById(id); }


    @Override
    @Transactional(readOnly = true)
    public Iterable<Venta> findAll() { return ventarepository.findAll(); }

    @Override
    @Transactional
    public Venta save(Venta venta) { return ventarepository.save(venta); }

    @Override
    @Transactional
    public void delete(Venta venta) { ventarepository.delete(venta); }

    @Override
    @Transactional
    public void deleteById(Integer id) { ventarepository.deleteById(id); }
}