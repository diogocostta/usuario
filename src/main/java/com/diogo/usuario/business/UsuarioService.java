package com.diogo.usuario.business;

import com.diogo.usuario.business.converter.UsuarioConverter;
import com.diogo.usuario.business.dto.UsuarioDTO;
import com.diogo.usuario.infrastructure.entity.Usuario;
import com.diogo.usuario.infrastructure.exceptions.ConflictException;
import com.diogo.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.diogo.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvarUsuario (UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuario(usuarioRepository.save(usuario));
    }
    public void emailExiste(String email){
        try{
            boolean existe = verificarEmailExistente(email);
            if(existe){
                throw new ConflictException("Email já cadastrado " + email);
            }
        }   catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado ", e.getCause());
        }

    }
    public boolean verificarEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }
    public Usuario buscarUsuarioPorEmail (String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email));
    }
    public void deletarUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

}

