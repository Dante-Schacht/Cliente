package com.Clientes.controllers;

import com.Clientes.dto.ClientesDTO;
import com.Clientes.services.ClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    @Autowired
    private ClientesService service;

    @PostMapping
    public ResponseEntity<ClientesDTO> crear(@RequestBody ClientesDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClientesDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // Este será el único método GET para obtener por id con HATEOAS
   
    @GetMapping("/{id}")
    public ResponseEntity<ClientesDTO> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
            .map(cliente -> {
            ClientesDTO dto = new ClientesDTO();
            dto.setIdCliente(cliente.getIdCliente());
            dto.setNombreCompleto(cliente.getNombreCompleto());
            dto.setRut(cliente.getRut());
            dto.setDireccion(cliente.getDireccion());
            dto.setTelefono(cliente.getTelefono());

            dto.add(linkTo(methodOn(ClientesController.class).listar()).withRel("Todos los clientes"));

            return ResponseEntity.ok(dto);
        })
        .orElse(ResponseEntity.notFound().build());
}

    @PutMapping("/{id}")
    public ResponseEntity<ClientesDTO> actualizar(@PathVariable Integer id, @RequestBody ClientesDTO dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
                
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return service.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
