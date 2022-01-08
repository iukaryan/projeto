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

import br.edu.ifrn.crud.dominio.Raca;
import br.edu.ifrn.crud.repository.RacaRepository;

@Controller
@RequestMapping("/racas")
public class buscaRacaController {
	
	@Autowired
	private RacaRepository repository;

	@GetMapping("/busca")
	public String entrarBusca() {

		return "usuario/buscaRaca";
	}

	@GetMapping("/buscar")
	public String buscar(@RequestParam(name = "nome", required = false) String nome,
			ModelMap model) {

		List<Raca> racasEncontradas = repository.findByNome(nome);

		model.addAttribute("racasEncontradas", racasEncontradas);

		return "usuario/buscaRaca";
	}

	@GetMapping("/remover/{id}")
	public String iniciarRemoção(@PathVariable("id") Integer idP, RedirectAttributes attr) {

		repository.deleteById(idP);
		attr.addFlashAttribute("msgSucesso", "Raca removida com sucesso");

		return "redirect:/racas/buscar";
	}

}
