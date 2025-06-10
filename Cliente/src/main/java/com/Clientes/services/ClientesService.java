package com.Clientes.services;

import com.Clientes.dto.ClientesDTO;
import com.Clientes.models.Clientes;
import com.Clientes.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientesService {

    @Autowired
    private ClientesRepository repository;

    public ClientesDTO guardar(ClientesDTO dto) {
        Clientes cliente = toEntity(dto);
        Clientes saved = repository.save(cliente);
        return toDTO(saved);
    }

    public List<ClientesDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClientesDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    public Optional<ClientesDTO> actualizar(Integer id, ClientesDTO dto) {
        return repository.findById(id).map(cliente -> {
            cliente.setIdUsuario(dto.getIdUsuario());
            cliente.setNombreCompleto(dto.getNombreCompleto());
            cliente.setRut(dto.getRut());
            cliente.setDireccion(dto.getDireccion());
            cliente.setTelefono(dto.getTelefono());
            return toDTO(repository.save(cliente));
        });
    }

    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // Métodos auxiliares
    private ClientesDTO toDTO(Clientes cliente) {
        ClientesDTO dto = new ClientesDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setIdUsuario(cliente.getIdUsuario());
        dto.setNombreCompleto(cliente.getNombreCompleto());
        dto.setRut(cliente.getRut());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }

    private Clientes toEntity(ClientesDTO dto) {
        Clientes cliente = new Clientes();
        cliente.setIdCliente(dto.getIdCliente());
        cliente.setIdUsuario(dto.getIdUsuario());
        cliente.setNombreCompleto(dto.getNombreCompleto());
        cliente.setRut(dto.getRut());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        return cliente;
    }
}