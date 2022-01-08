package br.edu.ifrn.crud.controller;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crud.dominio.Personagem;
import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.repository.PersonagemRepository;

@Controller
@RequestMapping("/personagens")
public class buscaPersonagemController {
	
	@Autowired
	private PersonagemRepository repository;

	@GetMapping("/busca")
	public String entrarBusca() {

		return "usuario/buscaPersonagem";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/buscar")
	public String buscar(@RequestParam(name = "nome", required = false) String nome,
			@RequestParam(name = "mostrarTodosDados", required = false) Boolean mostrarTodosDados,
			ModelMap model

	) {

		List<Personagem> personagensEncontrados = repository.findByNome(nome);

		model.addAttribute("personagensEncontrados", personagensEncontrados);

		if (mostrarTodosDados != null) {
			model.addAttribute("mostrarTodosDados", true);
		}

		return "usuario/buscaPersonagem";
	}

	@GetMapping("/remover/{id}")
	public String iniciarRemoção(@PathVariable("id") Integer idP, RedirectAttributes attr) {

		repository.deleteById(idP);
		attr.addFlashAttribute("msgSucesso", "Personagem removido com sucesso");

		return "redirect:/personagens/buscar";
	}

}
