package com.Clientes.assembler;

import com.Clientes.controllers.ClientesController;
import com.Clientes.dto.ClientesDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ClientesModelAssembler implements RepresentationModelAssembler<ClientesDTO, EntityModel<ClientesDTO>> {

    @Override
    public EntityModel<ClientesDTO> toModel(ClientesDTO dto) {
        return EntityModel.of(dto,
            linkTo(methodOn(ClientesController.class).obtener(dto.getIdCliente())).withSelfRel(),
            linkTo(methodOn(ClientesController.class).listar()).withRel("todos los clientes"),
            linkTo(methodOn(ClientesController.class).actualizar(dto.getIdCliente(), dto)).withRel("actualizar"),
            linkTo(methodOn(ClientesController.class).eliminar(dto.getIdCliente())).withRel("eliminar")
        );
    }
}
