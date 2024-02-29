package com.example.demo.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.*;

public interface ArticuloRepository extends JpaRepository<Articulo, String>{
}