package com.diogo.usuario.business;

import com.diogo.usuario.business.converter.UsuarioConverter;
import com.diogo.usuario.business.dto.EnderecoDTO;
import com.diogo.usuario.business.dto.TelefoneDTO;
import com.diogo.usuario.business.dto.UsuarioDTO;
import com.diogo.usuario.infrastructure.entity.Endereco;
import com.diogo.usuario.infrastructure.entity.Telefone;
import com.diogo.usuario.infrastructure.entity.Usuario;
import com.diogo.usuario.infrastructure.exceptions.ConflictException;
import com.diogo.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.diogo.usuario.infrastructure.repository.EnderecoRepository;
import com.diogo.usuario.infrastructure.repository.TelefoneRepository;
import com.diogo.usuario.infrastructure.repository.UsuarioRepository;
import com.diogo.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;



    public UsuarioDTO salvarUsuario (UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

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


    public UsuarioDTO buscarUsuarioPorEmail (String email){

        try{
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email)));
        }
        catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }


    public UsuarioDTO ataualizaDadosUsuario (String token, UsuarioDTO dto){

        //Aqui buscamos o email do usuário através do token (Tira a obrigatoriedade de passar o email)
        String email  = jwtUtil.extractUsername(token.substring(7));

        //Busca os dados do usuário no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não localizado"));

        //Mesclou os dados que recebemos na requisição DTO com os dados do banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        //Colocamos criptografia em nossa senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        //Salvou os dados do usuário convertido e depois pegou o retorno e converteu para UsuarioDTO 
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco (Long idEndereco, EnderecoDTO enderecoDTO){

        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado " + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

        return  usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }

    public TelefoneDTO atualizaTelefone (Long idTelefone, TelefoneDTO dto) {

        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }


}
