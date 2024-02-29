package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.Articulo;
import com.example.demo.modelo.Usuario;
import com.example.demo.modelo.Venta;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.VentaService;

@RestController
@RequestMapping ("/proyectoweb/ventas")
public class ControladorVenta{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private static ControladorVenta instancia;
	
	private ControladorVenta() {}
	
	public static ControladorVenta getInstancia() {
		if(instancia == null)
			instancia = new ControladorVenta();
		return instancia;
	}
	
	@Autowired  //Llama articuloService
	private VentaService ventaservice;
	
	@Autowired
	private UsuarioService usuarioservice;
	
	@PostMapping //Mando cuerpo con atributos de usuario y guardo en bd
	public ResponseEntity<?> create(@RequestBody Venta venta) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ventaservice.save(venta));
	}
	
	@GetMapping //Devuelve Articulos
	public List<Venta> readAll(@RequestParam String usuario) {
		
		Optional<Usuario> u = usuarioservice.findByNombreUsuario(usuario);
		if (!u.get().isEmpleado())
			return null;
		
		List<Venta> venta= StreamSupport
				.stream(ventaservice.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return venta;
	}
	
	@GetMapping("/compras")
	public List<Venta> compras(@RequestParam String usuario) {
		
		Usuario u = usuarioservice.findByNombreUsuario(usuario).get();
		
		List<Venta> ventas = StreamSupport
				.stream(ventaservice.findAll().spliterator(), false)
				.collect(Collectors.toList());
		
		List<Venta> compras = new ArrayList<Venta>();
		for (Venta v : ventas)
			if (v.getUsuario().equals(u))
				compras.add(v);
		
		return compras;
	}
	
	
	@GetMapping("/{id}") //Busca Articulo por ID - codigo
	public ResponseEntity<?> read(@PathVariable Integer id, @RequestParam String usuario) {
		
		Optional<Usuario> u = usuarioservice.findByNombreUsuario(usuario);
		if (!u.get().isEmpleado())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No tiene permisos para realizar esta acción");
		
		Optional<Venta> venta= ventaservice.findById(id);
		if (!venta.isPresent())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(venta);
	}
	
	@PutMapping("/entregar/{id}") // Seleccionar ID especifico para EDITAR
	public ResponseEntity<?> entregar(@PathVariable Integer id) {
		
		Optional<Venta> venta = ventaservice.findById(id);
		if (!venta.isPresent())
			return ResponseEntity.notFound().build();
		venta.get().setEntregado(true);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(ventaservice.save(venta.get()));
	}

	@DeleteMapping("/{id}") // Busca ID y BORRAR
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		if (!ventaservice.findById(id).isPresent())
			return ResponseEntity.notFound().build();
		ventaservice.deleteById(id);
		return ResponseEntity.ok().build();
	}
}