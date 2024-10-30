package com.autobots.automanager.services;

import com.autobots.automanager.controles.ClienteControle;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ClienteServices {

    @Autowired
    private ClienteRepositorio repositorio;

    public Cliente buscarCliente(Long id) {
        try {
            Cliente cliente = repositorio.findById(id).orElseThrow(
                    () -> new RuntimeException("Cliente não encontrado")
            );
            cliente.add(
                    linkTo(methodOn(ClienteControle.class).obterCliente(cliente.getId())).withSelfRel().withType("GET"),
                    linkTo(methodOn(ClienteControle.class).obterClientes()).withRel("clientes").withType("GET")
            );

            return cliente;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente");
        }
    }

    public List<Cliente> listarClientes() {
        try {
            List<Cliente> clientes = repositorio.findAll();
            clientes.forEach(cliente -> {
                cliente.add(
                        linkTo(methodOn(ClienteControle.class).obterCliente(cliente.getId())).withSelfRel().withType("GET"),
                        linkTo(methodOn(ClienteControle.class).obterClientes()).withRel("clientes").withType("GET")
                );
            });

            return clientes;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar clientes");
        }
    }

}
