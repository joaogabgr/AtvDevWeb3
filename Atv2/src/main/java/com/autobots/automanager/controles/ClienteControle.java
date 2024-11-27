package com.autobots.automanager.controles;

import java.util.List;

import com.autobots.automanager.services.ClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.ClienteAtualizador;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/cliente")
public class ClienteControle {
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private ClienteSelecionador selecionador;
	@Autowired
	private ClienteServices services;

	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> obterCliente(@PathVariable long id) {
		Cliente cliente = services.buscarCliente(id);
		return ResponseEntity.ok().body(cliente);
	}

	@GetMapping("/clientes")
	public ResponseEntity<?> obterClientes() {
		List<Cliente> clientes = services.listarClientes();
		return ResponseEntity.ok().body(clientes);
	}

	@PostMapping("/cadastro")
	public void cadastrarCliente(@RequestBody Cliente cliente) {
		repositorio.save(cliente);
	}

	@PutMapping("/atualizar")
	public void atualizarCliente(@RequestBody Cliente atualizacao) {
		Cliente cliente = repositorio.getById(atualizacao.getId());
		ClienteAtualizador atualizador = new ClienteAtualizador();
		atualizador.atualizar(cliente, atualizacao);
		repositorio.save(cliente);
	}

	@DeleteMapping("/excluir")
	public void excluirCliente(@RequestBody Cliente exclusao) {
		Cliente cliente = repositorio.getById(exclusao.getId());
		repositorio.delete(cliente);
	}
}
