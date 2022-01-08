package br.edu.ifrn.crud.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crud.dominio.Arquivo;
import br.edu.ifrn.crud.dominio.Jogo;
import br.edu.ifrn.crud.dominio.Personagem;
import br.edu.ifrn.crud.dominio.Raca;
import br.edu.ifrn.crud.repository.JogoRepository;
import br.edu.ifrn.crud.repository.RacaRepository;

@Controller
@RequestMapping("/jogos")
public class cadastroJogoController {
	
	@Autowired
	private JogoRepository repository;

	@GetMapping("/cadastro")
	public String entrarCadastro(ModelMap model) {
		model.addAttribute("jogo", new Jogo());
		return "usuario/cadastroJogo";
	}

	@PostMapping("/salvar")
	public String salvar(Jogo jogo, RedirectAttributes attr, ModelMap model) {
		
		repository.save(jogo);
		attr.addFlashAttribute("msgSucesso", "Jogo inserido com sucesso");
		
		return "redirect:/jogos/cadastro";
	}

	@GetMapping("/editar/{id}")
	public String iniciarEdição(@PathVariable("id") Integer idP, ModelMap model) {
		
		Jogo j = repository.findById(idP).get();
		
		model.addAttribute("jogo", j);
		
		return "/usuario/cadastroJogo";
	}

	private List<String> validarDados(Personagem personagem) {

		List<String> msgs = new ArrayList();
		if (personagem.getNome() == null || personagem.getNome().isEmpty()) {
			msgs.add("ADICONE UM NOME!");
		}
		if (personagem.getRaca() == null || personagem.getRaca().equals("null")) {
			msgs.add("ADICIONE A RAÇA!");
		}
		if (personagem.getJogo() == null || personagem.getJogo().equals("null")) {
			msgs.add("ADICIONE O JOGO!");
		}

		return msgs;

	}

}
