package br.com.fiap.devops.nac02.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.devops.nac02.models.Usuario;
import br.com.fiap.devops.nac02.repository.UsuarioRepository;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

	private UsuarioRepository repository;

	public UsuarioController(UsuarioRepository repository) {
		this.repository = repository;
	}

	@GetMapping("pesquisar")
	public String buscar(String nome, Model model) {
		var lista = repository.findByNomeContainsIgnoreCase(nome);
		model.addAttribute("usuarios", lista);
		return "usuario/index";
	}

	@GetMapping("editar/{id}")
	public String atualizar(@PathVariable int id, Model model) {
		var usuario = repository.findById(id).get();
		model.addAttribute("usuario", usuario);
		return "usuario/form";
	}

	@PostMapping("remover")
	public String deletar(int id, RedirectAttributes redirect) {
		repository.deleteById(id);
		redirect.addFlashAttribute("msg", "Usuário removido!");
		return "redirect:/usuario";
	}

	@GetMapping
	public String index(Model model) {
		var lista = repository.findAll();
		model.addAttribute("usuarios", lista);
		return "usuario/index";
	}

	@GetMapping("cadastrar")
	public String cadastrar(Usuario usuario) {
		return "usuario/form";
	}

	@PostMapping("salvar")
	public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes redirect) {

		if (result.hasErrors())
			return "usuario/form";

		if (usuario.getCodigo() == 0) {
			redirect.addFlashAttribute("msg", "Usuário cadastrado!");
		} else {
			redirect.addFlashAttribute("msg", "Usuário atualizado!");
		}

		repository.save(usuario);

		return "redirect:/usuario";
	}

}
