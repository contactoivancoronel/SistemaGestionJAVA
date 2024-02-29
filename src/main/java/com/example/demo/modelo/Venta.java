package com.example.demo.modelo;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="Venta")
@Table(name="ventas")

public class Venta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="carrito_id",referencedColumnName = "id")
	private Carrito carrito;
	
	public boolean entregado;
	
	public Venta(){
		
	}
	
	public Venta(Usuario usuario, Carrito carrito){
		this.usuario=usuario;
		this.carrito=carrito;
    }

	public Usuario getUsuario() {
		return usuario;
	}
	
	public Carrito getCarrito() {
		return carrito;
	}

	public boolean isEntregado() {
		return entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}

	/*
	private List<Articulo> stock;
    private List<String> historialVentas;
    private Map<String, Double> saldoUsuarios;
    private List<String> entregasRealizadas;
    
  //-------------------------------------------- CONSTRUCTOR ----------------------------------------------------------------------------------------------
    public Venta() {
    	this.stock = new ArrayList<>();
        this.historialVentas = new ArrayList<>();
        this.saldoUsuarios = new HashMap<>();
        this.entregasRealizadas = new ArrayList<>();
    }
    
//-------------------------------------------- METODOS DE ARTICULO ----------------------------------------------------------------------------------------------    

    public void cargarArticulo(Articulo articulo) {
        stock.add(articulo);
    }

    
    public void editarArticulo(String codigo, Articulo articuloActualizado) {
        for (int i = 0; i < stock.size(); i++) {
            Articulo articulo = stock.get(i);
            if (articulo.getCodigo().equals(codigo)) {
                stock.set(i, articuloActualizado);
                break;
            }
        }
    }
    
    
    public void eliminarArticulo(String codigo) {
        stock.removeIf(articulo -> articulo.getCodigo().equals(codigo));
    }

    
    
   public void verStock() {
        for (Articulo articulo : stock) {
            System.out.println("C�digo: " + articulo.getCodigo());
            System.out.println("Nombre: " + articulo.getNombre());
            System.out.println("Precio: " + articulo.getPrecio());
            System.out.println("------------------------");
        }
   }
   
   
   
   public List<Articulo> getStock() {
       return stock;
   }
   
   
 //-------------------------------------------- METODOS DE SALDO ----------------------------------------------------------------------------------------------

    public void agregarDinero(String nombreUsuario, double cantidad) {
        if (saldoUsuarios.containsKey(nombreUsuario)) {
            double saldoActual = saldoUsuarios.get(nombreUsuario);
            saldoUsuarios.put(nombreUsuario, saldoActual + cantidad);
        } else {
            saldoUsuarios.put(nombreUsuario, cantidad);
        }
    }

    
    
    public void transferirDinero(String origen, String destino, double cantidad) {
        if (saldoUsuarios.containsKey(origen) && saldoUsuarios.containsKey(destino)) {
            double saldoOrigen = saldoUsuarios.get(origen);
            double saldoDestino = saldoUsuarios.get(destino);

            if (saldoOrigen >= cantidad) {
                saldoUsuarios.put(origen, saldoOrigen - cantidad);
                saldoUsuarios.put(destino, saldoDestino + cantidad);
            } else {
                System.out.println("Saldo insuficiente en la cuenta de origen.");
            }
        } else {
            System.out.println("Las cuentas de origen y/o destino no existen.");
        }
    }

    
    
    public void removerDinero(String nombreUsuario, double cantidad) {
        if (saldoUsuarios.containsKey(nombreUsuario)) {
            double saldoActual = saldoUsuarios.get(nombreUsuario);
            if (saldoActual >= cantidad) {
                saldoUsuarios.put(nombreUsuario, saldoActual - cantidad);
            } else {
                System.out.println("Saldo insuficiente en la cuenta.");
            }
        } else {
            System.out.println("La cuenta de usuario no existe.");
        }
    }


  //-------------------------------------------- METODOS DE VENTA ----------------------------------------------------------------------------------------------
    
    public void agregarVenta(String detalleVenta) {
        historialVentas.add(detalleVenta);
    }

    
    
    public void verHistorialVentas() {
        for (String venta : historialVentas) {
            System.out.println(venta);
        }
    }

    
    
    public void marcarEntrega(String codigoVenta) {
        if (historialVentas.contains(codigoVenta)) {
            entregasRealizadas.add(codigoVenta);
            System.out.println("Venta marcada como entregada.");
        } else {
            System.out.println("La venta no existe en el historial.");
        }
    }

    
     
    public void verResumenVentas() {
        for (String venta : historialVentas) {
            System.out.println(venta);
            if (entregasRealizadas.contains(venta)) {
                System.out.println("Estado: Entregada");
            } else {
                System.out.println("Estado: Pendiente de entrega");
            }
            System.out.println("------------------------");
        }
    }
    
*/
}
