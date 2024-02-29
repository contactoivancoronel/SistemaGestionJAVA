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
import com.example.demo.modelo.Carrito;
import com.example.demo.modelo.Usuario;
import com.example.demo.modelo.Venta;
import com.example.demo.services.ArticuloService;
import com.example.demo.services.CarritoService;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.VentaService;

@RestController
@RequestMapping ("/proyectoweb/carritos")
public class ControladorCarrito {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private static ControladorCarrito instancia;
	
	private ControladorCarrito() {}
	
	public static ControladorCarrito getInstancia() {
		if(instancia == null)
			instancia = new ControladorCarrito();
		return instancia;
	}
	
	@Autowired  //Llama articuloService
	private CarritoService carritoservice;
	@Autowired 
	private ArticuloService articuloservice;
	@Autowired 
	private UsuarioService usuarioservice;
	@Autowired 
	private VentaService ventaservice;
	
	@PostMapping //Mando cuerpo con atributos de usuario y guardo en bd
	public ResponseEntity<?> create(@RequestBody Carrito carrito) {
		return ResponseEntity.status(HttpStatus.CREATED).body(carritoservice.save(carrito));
	}
	
	@GetMapping //Devuelve Articulos
	public List<Carrito> readAll() {
		List<Carrito> carrito= StreamSupport
				.stream(carritoservice.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return carrito;
	}
	
	@GetMapping("/{id}") //Busca Articulo por ID - codigo
	public ResponseEntity<?> read(@PathVariable Integer id) {
		Optional<Carrito> carrito= carritoservice.findById(id);
		if (!carrito.isPresent())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(carrito);
	}
	
	/*
	@PutMapping("/{id}") // Seleccionar ID especifico para EDITAR
	public ResponseEntity<?> update(@RequestParam String codigo, @PathVariable Integer id) {
		Optional<Carrito> carrito= carritoservice.findById(id);
		if (!carrito.isPresent())
			return ResponseEntity.notFound().build();
		Optional<Articulo> articulo = articuloservice.findById(codigo);
		carrito.get().agregarArticulo(articulo.get());
		return ResponseEntity.status(HttpStatus.CREATED).body(carritoservice.save(carrito.get()));
	}
	*/

	@DeleteMapping("/{id}") // Busca ID y BORRAR
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		if (!carritoservice.findById(id).isPresent())
			return ResponseEntity.notFound().build();
		carritoservice.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/abrir/{id}") // Seleccionar ID especifico para EDITAR
	public ResponseEntity<?> abrir(@RequestParam Integer idusuario, @PathVariable Integer id) {
		Carrito carrito = new Carrito(usuarioservice.findById(idusuario).get());
		Carrito c = carritoservice.save(carrito);
		return new ResponseEntity<Carrito>(c, HttpStatus.OK);
	}
	
	@PutMapping("/agregar/{id}") // Seleccionar ID especifico para EDITAR
	public ResponseEntity<?> agregarArticulo(@RequestParam String codigo, Integer cantidad, @PathVariable Integer id) {
		
		Optional<Carrito> carrito= carritoservice.findById(id);
		if (!carrito.isPresent())
			return ResponseEntity.notFound().build();
		
		Optional<Articulo> articulo= articuloservice.findById(codigo);
		
		if (articulo.get().getStock() < 1)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay stock suficiente de este artículo");
		
		if (carrito.get().getUsuario().getSaldo() < articulo.get().getPrecio())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente");
		
		carritoservice.agregarArticulo(articulo.get(), carrito.get(), cantidad);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(carritoservice.save(carrito.get()));
	}
	
	@PutMapping("/vaciar/{id}") // Seleccionar ID especifico para EDITAR
	public ResponseEntity<?> vaciarCarrito( @PathVariable Integer id) {
		Optional<Carrito> carrito= carritoservice.findById(id);
		if (!carrito.isPresent())
			return ResponseEntity.notFound().build();
		carritoservice.vaciarCarrito(carrito.get());
		return ResponseEntity.status(HttpStatus.CREATED).body(carritoservice.save(carrito.get()));
	}
	
	@PutMapping("/cerrar/{id}") // Seleccionar ID especifico para EDITAR
	public ResponseEntity<Venta> cerrarCarrito(@PathVariable Integer id) {
		Optional<Carrito> carrito= carritoservice.findById(id);
		if (!carrito.isPresent())
			return ResponseEntity.notFound().build();
		Venta v = carritoservice.cerrar(carrito.get());
		ventaservice.save(v);	
		return new ResponseEntity<Venta>(v,HttpStatus.OK);
	}
	
	@GetMapping("/articulos/{codigo}")
	public String articulos(@PathVariable String codigo) {
		
		Articulo a = articuloservice.findById(codigo).get();
		
		List<Carrito> carritos = StreamSupport
				.stream(carritoservice.findAll().spliterator(), false)
				.collect(Collectors.toList());
		
		List<Articulo> articulos = new ArrayList<Articulo>();
		for (Carrito c : carritos)
			if (c.getArticulos().contains(a))
					articulos.add(a);
		
		double total = 0;
		for (Articulo inx : articulos)
			total += inx.getPrecio();
		
		return ("El total recaudado es: $"+total);
	}
}