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

import com.example.demo.modelo.Usuario;
import com.example.demo.modelo.Venta;
import com.example.demo.services.UsuarioService;

@RestController
@RequestMapping ("/proyectoweb/usuarios")
public class ControladorUsuario {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private static ControladorUsuario instancia;
	
	private ControladorUsuario() {}
	
	public static ControladorUsuario getInstancia() {
		if(instancia == null)
			instancia = new ControladorUsuario();
		return instancia;
	}
	
	@Autowired  //Llama UsuarioService
	private UsuarioService usuarioservice;
	
	@PostMapping //Mando cuerpo con atributos de usuario y guardo en bd
	public ResponseEntity<?> create(@RequestBody Usuario usuario) {
		
		Optional<Usuario> u = usuarioservice.findByNombreUsuario(usuario.getNombreUsuario());
		if (u.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un usuario con ese nombre");
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioservice.save(usuario));
	}
	
	@PutMapping("/login")
	public ResponseEntity<?> login(@RequestParam String nombreUsuario, String contrasena) {
		
		Usuario usuario = usuarioservice.findByNombreUsuario(nombreUsuario).get();
		
		if (usuario == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe el usuario");
		
		if (usuarioservice.verificar(usuario, contrasena))
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioservice.save(usuario));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La contrasena es incorrecta");
	}
	
	@GetMapping //Devuelve Usuarios
	public List<Usuario> readAll() {
		List<Usuario> usuarios = StreamSupport
				.stream(usuarioservice.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return usuarios;
	}
	
	@GetMapping("/{id}") //Busca Usuario por ID
	public ResponseEntity<?> read(@PathVariable Integer id) {
		Optional<Usuario> usuario= usuarioservice.findById(id);
		if (!usuario.isPresent())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(usuario);
	}
	
	@PutMapping("/{id}") // Seleccionar ID especifico para EDITAR
	public ResponseEntity<?> update(@RequestParam String nombreUsuario, String contrasena, @PathVariable Integer id) {
		Optional<Usuario> usuario = usuarioservice.findById(id);
		if (!usuario.isPresent())
			return ResponseEntity.notFound().build();
		usuario.get().setNombreUsuario(nombreUsuario);
		usuario.get().setContrasena(contrasena);
	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioservice.save(usuario.get()));
	}
	
	@PutMapping("/cambiarRol/{id}") // Seleccionar ID especifico para EDITAR
	public ResponseEntity<?> cambiarRol(@PathVariable Integer id) {
		
		Optional<Usuario> usuario = usuarioservice.findById(id);
		
		if (!usuario.isPresent())
			return ResponseEntity.notFound().build();
		usuario.get().setEmpleado(!usuario.get().isEmpleado());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioservice.save(usuario.get()));
	}
	

	@DeleteMapping("/{id}") // Busca ID y BORRAR
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		if (!usuarioservice.findById(id).isPresent())
			return ResponseEntity.notFound().build();
		usuarioservice.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/agregarsaldo/{id}") // Seleccionar ID especifico para EDITAR
	public ResponseEntity<?> agregarSaldo(@RequestParam double dinero, @PathVariable Integer id) {
		Optional<Usuario> usuario = usuarioservice.findById(id);
		if (!usuario.isPresent())
			return ResponseEntity.notFound().build();

		usuarioservice.agregarSaldo(dinero, usuario.get());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioservice.save(usuario.get()));
	}
	
	@PutMapping("/removersaldo/{id}") // Seleccionar ID especifico para EDITAR
	public ResponseEntity<?> removerSaldo(@RequestParam double dinero, @PathVariable Integer id) {
		Optional<Usuario> usuario = usuarioservice.findById(id);
		if (!usuario.isPresent())
			return ResponseEntity.notFound().build();

		usuarioservice.removerSaldo(dinero, usuario.get());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioservice.save(usuario.get()));
	}
	
	@PutMapping("/transferir/{id}")
	public ResponseEntity<?> transferir(@RequestParam double dinero, Integer idReceptor, @PathVariable Integer id) {
		
		Optional<Usuario> emisor = usuarioservice.findById(id);
		if (!emisor.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo encontrar al emisor");
		
		Optional<Usuario> receptor = usuarioservice.findById(idReceptor);
		if (!receptor.isPresent())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo encontrar al receptor");

		if (emisor.get().getSaldo() < dinero)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El emisor no tiene saldo suficiente en su cuenta");
		
		usuarioservice.removerSaldo(dinero, emisor.get());
		usuarioservice.agregarSaldo(dinero, receptor.get());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioservice.save(receptor.get()));
	}
	
}