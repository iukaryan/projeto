package br.edu.ifrn.crud.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crud.dominio.Arquivo;
import br.edu.ifrn.crud.dominio.Jogo;
import br.edu.ifrn.crud.dominio.Personagem;
import br.edu.ifrn.crud.dominio.Raca;
import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.dto.AutoCompleteDTO;
import br.edu.ifrn.crud.repository.ArquivoRepository;
import br.edu.ifrn.crud.repository.JogoRepository;
import br.edu.ifrn.crud.repository.PersonagemRepository;
import br.edu.ifrn.crud.repository.RacaRepository;

@Controller
@RequestMapping("/personagens")
public class cadastroPersonagemController {
	
	@Autowired
	private PersonagemRepository repository;
	
	@Autowired
	private ArquivoRepository arquivoRepository;
	
	@Autowired
	private RacaRepository racaRepository;
	
	@Autowired
	private JogoRepository jogoRepository;

	@GetMapping("/cadastro")
	public String entrarCadastro(ModelMap model) {
		model.addAttribute("personagem", new Personagem());
		return "usuario/cadastroPersonagem";
	}

	@PostMapping("/salvar")
	public String salvar(Personagem personagem, RedirectAttributes attr, ModelMap model, 
			@RequestParam("file") MultipartFile arquivo) {
		try {
			List<String> validacao = validarDados(personagem);
			if(!validacao.isEmpty()) {
				model.addAttribute("msgErro",validacao);
				return "/usuario/cadastroEstudante";
			}
			if (arquivo != null && !arquivo.isEmpty()) {
				
				String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());
				Arquivo arquivoBD = new Arquivo(null, nomeArquivo, arquivo.getContentType(), arquivo.getBytes());

				arquivoRepository.save(arquivoBD);
				
				if (personagem.getFoto() != null && personagem.getFoto().getId() != null && personagem.getFoto().getId() > 0) {

					arquivoRepository.delete(personagem.getFoto());
				}

				personagem.setFoto(arquivoBD);
			} else {
				
				personagem.setFoto(null);
			}

			repository.save(personagem);

			attr.addFlashAttribute("msgSucesso", "Personagem inserido com sucesso");
			

		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return "redirect:/personagens/cadastro";
	}

	@GetMapping("/editar/{id}")
	public String iniciarEdição(@PathVariable("id") Integer idP, ModelMap model) {
		
		Personagem p = repository.findById(idP).get();
		
		model.addAttribute("personagem", p);
		
		return "/usuario/cadastroPersonagem";
	}
	
	@GetMapping("/autocompleteRacas")
	@Transactional(readOnly = true)
	@ResponseBody 					
	public List<AutoCompleteDTO> autocompleteRacas(
			@RequestParam("term") String termo){
		
		List<Raca> racas = racaRepository.findByNome(termo);
		
		List<AutoCompleteDTO> resultados =  new ArrayList<>();
		
		racas.forEach(p -> resultados.add(
					new AutoCompleteDTO(p.getNome(),p.getId())
				));
		return resultados;
	}
	
	@GetMapping("/autocompleteJogos")
	@Transactional(readOnly = true) 
	@ResponseBody 					
	public List<AutoCompleteDTO> autocompleteFormacoes(
			@RequestParam("term") String termo){
		
		List<Jogo> jogos = jogoRepository.findByNome(termo);
		
		
		List<AutoCompleteDTO> resultados =  new ArrayList<>();
		
	
		jogos.forEach(p -> resultados.add(
					new AutoCompleteDTO(p.getNome(),p.getId())
				));
		return resultados;
	}
	
	@PostMapping("/addJogos")
	public String addEstudante(Personagem personagem, ModelMap model) {
		if(personagem.getJogo() == null) {
			personagem.setJogo(new ArrayList<>());
		}
		personagem.getJogo().add(personagem.getTipoJogo());
		
		return "usuario/cadastroPersonagem"; 
	}
	@PostMapping("/removerJogos/{id}")
	public String removerEstudante(@PathVariable("id") Integer idJogo,
			Personagem personagem, 
			ModelMap model ) {
		
		Jogo jogos = new Jogo();
		
		jogos.setId(idJogo);
		
		personagem.getJogo().remove(jogos);
		
		return "usuario/cadastroPersonagem"; 
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
