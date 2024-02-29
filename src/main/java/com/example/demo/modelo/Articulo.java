package com.example.demo.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="Articulo")
@Table(name="articulos") //La entidad Articulo se corresponde con la tabla articulos
public class Articulo {
	
	
	
  //-------------------------------------------- ATRIBUTOS ----------------------------------------------------------------------------------------------
	@Id
	
	private String codigo;
    private String nombre;
    private double precio;
    private int stock;
    
    @OneToMany(mappedBy = "articulos")
    private List<Carrito> carritos;
    
  //-------------------------------------------- CONSTRUCTOR ----------------------------------------------------------------------------------------------
    
    public Articulo() {
   
}

	public Articulo(String codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
}
    
    
  //-------------------------------------------- GETTERS & SETTERS DE ARTICULO ----------------------------------------------------------------------------------------------

    //RETORNA CODIGO ART.
    public String getCodigo() {
        return codigo;
    }

    //SETEA CODIGO ART.
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

  //RETORNA NOMBRE ART.
    public String getNombre() {
        return nombre;
    }

  //SETEA NOMBRE ART.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

  //RETORNA PRECIO ART.
    public double getPrecio() {
        return precio;
    }

  //SETEA PRECIO ART.
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public int getStock() {
		return stock;
	}


	public void setStock(int stock) {
		this.stock = stock;
	}


}
