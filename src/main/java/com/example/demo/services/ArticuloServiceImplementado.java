package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.modelo.Articulo;
import com.example.demo.repositiries.ArticuloRepository;

@Service
public class ArticuloServiceImplementado implements ArticuloService {
	@Autowired
    private ArticuloRepository articulorepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Articulo> findById(String codigo) { return articulorepository.findById(codigo); }


    @Override
    @Transactional(readOnly = true)
    public Iterable<Articulo> findAll() { return articulorepository.findAll(); }

    @Override
    @Transactional
    public Articulo save(Articulo articulo) { return articulorepository.save(articulo); }

    @Override
    @Transactional
    public void delete(Articulo articulo) { articulorepository.delete(articulo); }

    @Override
    @Transactional
    public void deleteById(String codigo) { articulorepository.deleteById(codigo); }
}
