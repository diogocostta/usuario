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

    public Endereco paraEndereco (EnderecoDTO endereco){
        return Endereco.builder()
                .rua(endereco.getRua())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .complemento(endereco.getComplemento())
                .numero(endereco.getNumero())
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

    public UsuarioDTO paraUsuarioDTO (Usuario usuarioDTO){
        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecosDTO(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuarioDTO.getTelefones()))
                .build();

    }

    public List<EnderecoDTO> paraListaEnderecosDTO (List<Endereco> enderecoDTOS){
        return enderecoDTOS.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO (Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .complemento(endereco.getComplemento())
                .numero(endereco.getNumero())
                .build();
    }
    public List<TelefoneDTO> paraListaTelefonesDTO (List<Telefone> telefoneDTOS) {
        List<TelefoneDTO> telefones = new ArrayList<>();
        for (Telefone telefoneDTO : telefoneDTOS) {
            telefones.add(paraTelefoneDTO(telefoneDTO));
        }
        return telefones;
    }

    public TelefoneDTO paraTelefoneDTO (Telefone telefone){
        return TelefoneDTO.builder()
                .id((telefone.getId()))
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

    public Usuario updateUsuario (UsuarioDTO usuarioDTO, Usuario entity){
        return Usuario.builder()
                .id(entity.getId())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .build();

    }

    public Endereco updateEndereco (EnderecoDTO dto, Endereco entity) {
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .build();
    }

    public Telefone updateTelefone (TelefoneDTO dto, Telefone entity) {
        return  Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .build();
    }

    public Endereco paraEnderecoEntity (EnderecoDTO dto, Long idUsuario){
        //Não estamos passando o ID do endereço pois ele é gerado automaticamente quando é gerado um dado no banco de dados
        return Endereco.builder()
                .cep(dto.getCep())
                .estado(dto.getEstado())
                .cidade(dto.getCidade())
                .numero(dto.getNumero())
                .rua(dto.getRua())
                .complemento(dto.getComplemento())
                .usuario_id(idUsuario)
                .build();
    }
    public Telefone paraTelefoneEntity (TelefoneDTO dto, Long idUsuario){
        //Não estamos passando o ID do telefone pois ele é gerado automaticamente quando é gerado um dado no banco de dados
        return Telefone.builder()
               .numero(dto.getNumero())
               .ddd(dto.getDdd())
               .usuario_id(idUsuario)
               .build();
    }

}


