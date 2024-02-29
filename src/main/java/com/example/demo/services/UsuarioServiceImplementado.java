package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.modelo.Usuario;
import com.example.demo.modelo.Venta;
import com.example.demo.repositiries.UsuarioRepository;

@Service
public class UsuarioServiceImplementado implements UsuarioService {
	@Autowired
    private UsuarioRepository usuariorepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Integer id) { return usuariorepository.findById(id); }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByNombreUsuario(String nombre) { return usuariorepository.findByNombreUsuario(nombre); }


    @Override
    @Transactional(readOnly = true)
    public Iterable<Usuario> findAll() { return usuariorepository.findAll(); }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) { return usuariorepository.save(usuario); }

    @Override
    @Transactional
    public void delete(Usuario usuario) { usuariorepository.delete(usuario); }

    @Override
    @Transactional
    public void deleteById(Integer id) { usuariorepository.deleteById(id); }
    
    @Override
    @Transactional
    public void agregarSaldo(double dinero, Usuario usuario) {
    	usuario.agregarSaldo(dinero);
    }
    
    @Override
    @Transactional
    public void removerSaldo(double dinero, Usuario usuario) {
    	usuario.removerSaldo(dinero);
    }
    
    @Override
    @Transactional
    public boolean verificar(Usuario usuario, String contrasena) {
    	return usuario.verificar(contrasena);
    }
    
}
