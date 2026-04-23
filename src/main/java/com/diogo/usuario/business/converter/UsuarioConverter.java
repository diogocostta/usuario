package com.diogo.usuario.business.converter;

import com.diogo.usuario.business.dto.EnderecoDTO;
import com.diogo.usuario.business.dto.TelefoneDTO;
import com.diogo.usuario.business.dto.UsuarioDTO;
import com.diogo.usuario.infrastructure.entity.Endereco;
import com.diogo.usuario.infrastructure.entity.Telefone;
import com.diogo.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component

public class UsuarioConverter {

    public Usuario paraUsuario (UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecos(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefones(usuarioDTO.getTelefones()))

                .build();

    }

    public List<Endereco> paraListaEnderecos(List<EnderecoDTO> enderecoDTOS){
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco (EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .complemento(enderecoDTO.getComplemento())
                .numero(enderecoDTO.getNumero())
                .build();
    }
    public List<Telefone> paraListaTelefones (List<TelefoneDTO> telefoneDTOS) {
        List<Telefone> telefones = new ArrayList<>();
        for (TelefoneDTO telefoneDTO : telefoneDTOS) {
            telefones.add(paraTelefone(telefoneDTO));
        }
        return telefones;
    }

    public Telefone paraTelefone (TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }

    //Divisão de conversão de Entity para DTO

    public UsuarioDTO paraUsuario (Usuario usuarioDTO){
        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecosDTO(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuarioDTO.getTelefones()))
                .build();

    }

    public List<EnderecoDTO> paraListaEnderecosDTO (List<Endereco> enderecos){
        return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO (Endereco enderecoDTO){
        return EnderecoDTO.builder()
                .rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .complemento(enderecoDTO.getComplemento())
                .numero(enderecoDTO.getNumero())
                .build();
    }
    public List<TelefoneDTO> paraListaTelefonesDTO (List<Telefone> telefoneDTOS) {
        List<TelefoneDTO> telefones = new ArrayList<>();
        for (Telefone telefoneDTO : telefoneDTOS) {
            telefones.add(paraTelefoneDTO(telefoneDTO));
        }
        return telefones;
    }

    public TelefoneDTO paraTelefoneDTO (Telefone telefoneDTO){
        return TelefoneDTO.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }

}


