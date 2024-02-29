package com.example.demo.repositiries;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Carrito;


public interface CarritoRepository extends JpaRepository<Carrito, Integer>{
}
