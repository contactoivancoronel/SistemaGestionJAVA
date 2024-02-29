package com.example.demo.modelo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="Carrito")
@Table(name="carritos")

public class Carrito {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	
	@OneToOne(mappedBy="carrito", cascade=CascadeType.ALL)
	private Venta venta;
	
	@ManyToMany(targetEntity=Articulo.class,cascade=CascadeType.ALL)
	@JoinTable(name = "carritos_articulos",
				joinColumns = @JoinColumn(name = "id"),
				inverseJoinColumns = @JoinColumn(name = "codigo"))
	List<Articulo> articulos;
	/*
	@ElementCollection
	@CollectionTable(name = "carritos_articulos",
	            	  joinColumns = @JoinColumn(name = "id"))
	@MapKeyJoinColumn(name = "codigo")
	@Column(name = "cantidad")
	private Map<Articulo, Integer> cantidades;
	*/
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;

//-------------------------------------------- CONSTRUCTOR ----------------------------------------------------------------------------------------------
	
	public Carrito() {}
	
    public Carrito(Usuario usuario) {
        this.articulos = new ArrayList<>(); //CREO LISTA VACIA
        //this.cantidades = new HashMap<>();
        this.usuario = usuario;
    }
    
    
    
//-------------------------------------------- METODOS ----------------------------------------------------------------------------------------------    
 
    public Usuario getUsuario() {
		return usuario;
	}
/*
	public Map<Articulo, Integer> getCantidades() {
		return cantidades;
	}
*/
	//ABRIR CARRITO 
    public void abrir(Usuario usuario){
    	this.usuario = usuario;
    }
    
    // AGREGAR ART AL CARRITO
    public void agregarArticulo(Articulo articulo, Integer cantidad) {
        articulos.add(articulo);
       //cantidades.put(articulo, cantidad);
    }
    
 // LIMPIAR CARRO
    public void vaciarCarrito() {
        articulos.clear();
    }

 // PRECIO TOTAL DEL CARRO 
    public double calcularPrecioTotal() {
        double total = 0.0;
        for (Articulo articulo : articulos) {
            total += articulo.getPrecio();
        }
        return total;
    }
    
    
    // RETORNO ARTICULOS DEL CARRO 
    public List<Articulo> getArticulos() {
        return articulos;
    }
    
    //CERRAR CARRITO
    public Venta cerrar() {
    	for (Articulo articulo : articulos) {
            articulo.setStock(articulo.getStock()-1);
        }
    	Venta venta = new Venta(this.usuario,this);
    	this.usuario.removerSaldo(calcularPrecioTotal());
    	this.usuario.agregarCompra(venta);
    	return venta;
    }
    

}
