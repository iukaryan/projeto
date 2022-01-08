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

import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class buscaUsuarioController {
	
	@Autowired
	private UsuarioRepository repository;

	@GetMapping("/busca")
	public String entrarBusca() {

		return "usuario/buscaUsuario";
	}

	@GetMapping("/buscar")
	public String buscar(@RequestParam(name = "nome", required = false) String nome,
			@RequestParam(name = "mostrarTodosDados", required = false) Boolean mostrarTodosDados,
			ModelMap model
	) {

		
		List<Usuario> usuariosEncontrados = repository.findByNome(nome);

		model.addAttribute("usuariosEncontrados", usuariosEncontrados);

		if (mostrarTodosDados != null) {
			model.addAttribute("mostrarTodosDados", true);
		}

		return "usuario/buscaUsuario";
	}

	@GetMapping("/remover/{id}")
	public String iniciarRemoção(@PathVariable("id") Integer idUsuario, RedirectAttributes attr) {

		repository.deleteById(idUsuario);
		attr.addFlashAttribute("msgSucesso", "Usuario removido com sucesso");

		return "redirect:/usuarios/buscar";
	}

}
