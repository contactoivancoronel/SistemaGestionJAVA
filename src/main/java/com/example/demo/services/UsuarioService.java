    package com.example.demo.services;

    import java.util.List;
    import java.util.Optional;

    import com.example.demo.modelo.Usuario;
    import com.example.demo.modelo.Venta;

    public interface UsuarioService {
        
        public Optional<Usuario> findById(Integer id);
        
        public Optional<Usuario> findByNombreUsuario(String nombre);

        public Iterable<Usuario> findAll();

        public Usuario save(Usuario usuario);

        public void delete(Usuario usuario);

        public void deleteById(Integer id);
        
        public void agregarSaldo(double dinero, Usuario usuario);
        
        public void removerSaldo(double dinero, Usuario usuario);
        
        public boolean verificar(Usuario usuario, String contrasena);
        
    }
