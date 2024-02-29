package com.example.demo.controllers;

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
import com.example.demo.services.ArticuloService;
import com.example.demo.services.UsuarioService;



@RestController
@RequestMapping ("/proyectoweb/articulos")
public class ControladorArticulo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private static ControladorArticulo instancia;
	
	private ControladorArticulo() {}
	
	public static ControladorArticulo getInstancia() {
		if(instancia == null)
			instancia = new ControladorArticulo();
		return instancia;
	}
	
	@Autowired  //Llama articuloService
	private ArticuloService articuloservice;
	
	@Autowired
	private UsuarioService usuarioservice;
	
	@PostMapping //Mando cuerpo con atributos de usuario y guardo en bd
	public ResponseEntity<?> create(@RequestBody Articulo articulo, @RequestParam String usuario) {
		
		Optional<Usuario> u = usuarioservice.findByNombreUsuario(usuario);
		if (!u.get().isEmpleado())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No tiene permisos para realizar esta acción");
		
		return ResponseEntity.status(HttpStatus.CREATED).body(articuloservice.save(articulo));
	}
	
	@GetMapping //Devuelve Articulos
	public List<Articulo> readAll() {
		List<Articulo> articulo = StreamSupport
				.stream(articuloservice.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return articulo;
	}
	
	@GetMapping("/{codigo}") //Busca Articulo por ID - codigo
	public ResponseEntity<?> read(@PathVariable String codigo) {
		Optional<Articulo> articulo= articuloservice.findById(codigo);
		if (!articulo.isPresent())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(articulo);
	}
	
	@PutMapping("/{codigo}") // Seleccionar ID especifico para EDITAR
	public ResponseEntity<?> update(@RequestParam String nombre, double precio, String usuario, @PathVariable String codigo) {
		
		Optional<Usuario> u = usuarioservice.findByNombreUsuario(usuario);
		if (!u.get().isEmpleado())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No tiene permisos para realizar esta acción");
		
		Optional<Articulo> articulo = articuloservice.findById(codigo);
		if (!articulo.isPresent())
			return ResponseEntity.notFound().build();
		articulo.get().setNombre(nombre);
		articulo.get().setPrecio(precio);
	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(articuloservice.save(articulo.get()));
	}
	
	@PutMapping("/reponer/{codigo}") // Seleccionar ID especifico para EDITAR
	public ResponseEntity<?> reponer(@RequestParam int stock, String usuario, @PathVariable String codigo) {
		
		Optional<Usuario> u = usuarioservice.findByNombreUsuario(usuario);
		if (!u.get().isEmpleado())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No tiene permisos para realizar esta acción");
		
		Optional<Articulo> articulo = articuloservice.findById(codigo);
		if (!articulo.isPresent())
			return ResponseEntity.notFound().build();
		articulo.get().setStock(articulo.get().getStock() + stock);
		return ResponseEntity.status(HttpStatus.CREATED).body(articuloservice.save(articulo.get()));
	}
	

	@DeleteMapping("/{codigo}") // Busca ID y BORRAR
	public ResponseEntity<?> delete(@PathVariable String codigo, @RequestParam String usuario) {
		
		Optional<Usuario> u = usuarioservice.findByNombreUsuario(usuario);
		if (!u.get().isEmpleado())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No tiene permisos para realizar esta acción");
		
		if (!articuloservice.findById(codigo).isPresent())
			return ResponseEntity.notFound().build();
		articuloservice.deleteById(codigo);
		return ResponseEntity.ok().build();
	}
	

}