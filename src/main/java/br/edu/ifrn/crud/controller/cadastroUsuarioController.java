package br.edu.ifrn.crud.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class cadastroUsuarioController {
	
	@Autowired
	private UsuarioRepository repository;

	@GetMapping("/cadastro")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String entrarCadastro(ModelMap model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/cadastroUsuario";
	}

	@PostMapping("/salvar")
	public String salvar(Usuario usuario, RedirectAttributes attr, ModelMap model) {

		List<String> msgValidacao = validarDados(usuario);

		if (!msgValidacao.isEmpty()) {
			model.addAttribute("msgsErro", msgValidacao);
			return "usuario/cadastroUsuario";
		}
		
		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		
		repository.save(usuario);
		
		attr.addFlashAttribute("msgSucesso", "Operação realizada com sucesso");
		
		return "redirect:/usuarios/cadastro";
	}

	@GetMapping("/editar/{id}")
	public String iniciarEdição(@PathVariable("id") Integer idUsuario, ModelMap model) {
		
		Usuario u = repository.findById(idUsuario).get();
		
		model.addAttribute("usuario", u);
		
		return "/usuario/cadastroUsuario";
	}

	private List<String> validarDados(Usuario usuario) {

		List<String> msgs = new ArrayList();
		if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
			msgs.add("ADICONE UM NOME!");
		}
		if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			msgs.add("ADICIONE O SEU EMAIL!");
		}
		if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
			msgs.add("ADICIONE UMA SENHA VÁLIDA!");
		}
		if (usuario.getCidade() == null || usuario.getCidade().isEmpty()) {
			msgs.add("ADICIONE A SUA CIDADE!");
		}
		if (usuario.getEstado() == null || usuario.getEstado().equals("null")) {
			msgs.add("ADICIONE O SEU ESTADO!");
		}

		return msgs;

	}

}