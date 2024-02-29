package com.example.demo.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer>{
}
