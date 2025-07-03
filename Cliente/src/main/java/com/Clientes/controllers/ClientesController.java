package com.Clientes.controllers;

import com.Clientes.assembler.ClientesModelAssembler;
import com.Clientes.dto.ClientesDTO;
import com.Clientes.services.ClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    public ResponseEntity<CollectionModel<EntityModel<ClientesDTO>>> listar() {
        List<EntityModel<ClientesDTO>> clientes = service.listar().stream()
            .map(assembler::toModel)
            .toList();

    return ResponseEntity.ok(
        CollectionModel.of(clientes,
            linkTo(methodOn(ClientesController.class).listar()).withSelfRel()
        )
    );
}
   
    @Autowired
private ClientesModelAssembler assembler;

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClientesDTO>> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(cliente -> ResponseEntity.ok(assembler.toModel(cliente)))
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
