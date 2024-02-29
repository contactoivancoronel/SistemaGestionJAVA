package com.example.demo.modelo;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="Usuario")
@Table(name="usuarios") //La entidad Usuario se corresponde con la tabla usuarios
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nombreUsuario;
	
    private String contrasena;
    
    private double saldo;
    
    private boolean empleado;
    
    @OneToMany(mappedBy="usuario", cascade = CascadeType.ALL) //Si elimino usuario, se elimina con el las ventas.
    private List<Venta> compras;
    
    @OneToMany(mappedBy="usuario", cascade = CascadeType.ALL)
    private List<Venta> ventas;
    
    @OneToMany(mappedBy="usuario", cascade = CascadeType.ALL)
    private List<Carrito> carritos;
    
  //-------------------------------------------- CONSTRUCTOR ----------------------------------------------------------------------------------------------
    public Usuario() {
    	
    }
    
    public Usuario(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.saldo = 0;
        this.empleado = false;
    }
    
    
  //-------------------------------------------- GETTERS & SETTERS ----------------------------------------------------------------------------------------------

	public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public boolean isEmpleado() {
		return empleado;
	}

	public void setEmpleado(boolean empleado) {
		this.empleado = empleado;
	}
	
	 //-------------------------------------------- modulos ----------------------------------------------------------------------------------------------
	
	public void agregarSaldo(double dinero){
		this.saldo += dinero;
	}
	public void removerSaldo(double dinero){
		this.saldo -= dinero;
	}

	public boolean verificar(String contrasena) {
		return (this.contrasena.equals(contrasena));
	}

	public void agregarCompra(Venta venta) {
		this.compras.add(venta);
	}

}
