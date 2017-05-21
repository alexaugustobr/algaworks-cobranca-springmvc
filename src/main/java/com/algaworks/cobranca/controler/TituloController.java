package com.algaworks.cobranca.controler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.TituloTipo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;
import com.algaworks.cobranca.repository.TitulosTipo;
import com.algaworks.cobranca.service.CadastroTituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	@Autowired
	private Titulos titulos;
	@Autowired
	private CadastroTituloService cadastroTituloService;

	@Autowired
	private TitulosTipo titulosTipo;
	private static final String CADASTRO_VIEW = "CadastroTitulo";
	private static final String REDIRECT_CADASTRO_VIEW = "redirect:/titulos/novo";
	private static final String REDIRECT_TITULOS_VIEW = "redirect:/titulos";

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("titulo", new Titulo());
		// mv.addObject("todosStatusTitulo",StatusTitulo.values());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors erros, RedirectAttributes attributes) {

		if (erros.hasErrors()) {
			// ModelAndView mv = new ModelAndView("redirect:/titulos/novo");
			// attributes.addFlashAttribute("org.springframework.validation.BindingResult.register",
			// erros);
			// attributes.addFlashAttribute("titulo", titulo);
			return CADASTRO_VIEW;
		} else {
			try {
				if (titulo.getCodigo() != null) {
					attributes.addFlashAttribute("mensagem", "Titulo editado com sucesso!");
				} else {
					attributes.addFlashAttribute("mensagem", "Titulo salvo com sucesso!");
				}
				cadastroTituloService.salvar(titulo);
				
				return REDIRECT_CADASTRO_VIEW;
			} catch (IllegalArgumentException e) {
				erros.rejectValue("dataVencimento", null, e.getMessage());
				return CADASTRO_VIEW;
			}

			// mv.addObject("mensagem", "Titulo salvo com sucesso!");

		}
	}
	
	@RequestMapping(value="/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable long codigo) {
		//System.out.println("PUT Codigo a receber "+codigo);
		cadastroTituloService.receber(codigo);
		return StatusTitulo.RECEBIDO.getDescricao();//vai para o javascript  
	}

	@RequestMapping
	public ModelAndView pesquisar() {
		List<Titulo> todosTitulos = titulos.findAll();
		// List<Titulo> todosTitulos = titulos.descricaoTipo("Salário");

		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos", todosTitulos);
		// mv.addObject("todosStatusTitulo",StatusTitulo.values());
		return mv;
	}

	@RequestMapping("{codigo}")
	// @PathVariable Long codigo
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		// Titulo titulo = titulos.getOne(codigo);
		mv.addObject("titulo", titulo);
		return mv;
	}

	// titulo/codigo
	@RequestMapping(value = "{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable long codigo, RedirectAttributes attributes) {
		
		try {
			cadastroTituloService.excluir(codigo);
			attributes.addFlashAttribute("mensagem", "Titulo excluido com sucesso!");
			return REDIRECT_TITULOS_VIEW;
		} catch (Exception e) {
			List<String> mensagensErro = new ArrayList<String>();
			mensagensErro.add("Não foi possivel excluir o titulo!");
			mensagensErro.add(e.toString());
			//lista de erros para a view, cada uma se transforma em uma mensagem
			attributes.addFlashAttribute("mensagenserro", mensagensErro);
			for (String erro : mensagensErro) {
				System.out.println("Erro: " + erro);
			}
			return REDIRECT_TITULOS_VIEW;
		}

	}

	// lista que vai ser diretamente chamada pelo thymeleaf tela cadastro
	// vai com o nome de statusTituloList
	@ModelAttribute
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}

	// lista volta com o nome de tituloTipoList
	@ModelAttribute
	public List<TituloTipo> todosTiposTitulo() {
		List<TituloTipo> tituloTipoList = titulosTipo.findAll();
		return tituloTipoList;
	}

	@RequestMapping("/tipo")
	public ModelAndView novoTipo() {
		ModelAndView mv = new ModelAndView("CadastroTituloTipo");
		mv.addObject("tituloTipo", new TituloTipo());
		// mv.addObject("todosStatusTitulo",StatusTitulo.values());
		return mv;
	}

	@RequestMapping("/tipo/salvartipo")
	public String salvarTipo(@Validated TituloTipo tituloTipo, Errors erros, RedirectAttributes attributes) {

		if (erros.hasErrors()) {
			// ModelAndView mv = new ModelAndView("redirect:/titulos/novo");
			// attributes.addFlashAttribute("org.springframework.validation.BindingResult.register",
			// erros);
			// attributes.addFlashAttribute("titulo", titulo);
			return "CadastroTituloTipo";
		} else {
			if (tituloTipo.getCodigo() != null) {
				titulosTipo.save(tituloTipo);
				attributes.addFlashAttribute("mensagem", "Tipo de Titulo salvo com sucesso!");
			} else {
				titulosTipo.save(tituloTipo);
				attributes.addFlashAttribute("mensagem", "Tipo de Titulo editado com sucesso!");
			}

			// mv.addObject("mensagem", "Titulo salvo com sucesso!");
			return "redirect:/titulos/tipo";
		}
	}

	@RequestMapping("tipo/{codigo}")
	// @PathVariable Long codigo
	public ModelAndView edicaoTipo(@PathVariable("codigo") TituloTipo tituloTipo) {

		ModelAndView mv = new ModelAndView("CadastroTituloTipo");
		// Titulo titulo = tituloTipo.getOne(codigo);
		mv.addObject("tituloTipo", tituloTipo);
		return mv;
	}

	/*
	 * @RequestMapping(method = RequestMethod.POST) public String
	 * salvar(@ModelAttribute("titulo") @Validated Titulo titulo, BindingResult
	 * biding, RedirectAttributes attributes, HttpSession session) {
	 * 
	 * if (biding.hasErrors()) { attributes.addFlashAttribute(
	 * "org.springframework.validation.BindingResult.register", biding);
	 * attributes.addFlashAttribute("titulo", titulo); return
	 * "redirect:/titulos/novo"; } else { titulos.save(titulo);
	 * attributes.addFlashAttribute("mensagem", "Titulo salvo com sucesso!");
	 * titulo = new Titulo(); attributes.addFlashAttribute("titulo", titulo);
	 * return "redirect:/titulos/novo"; } }
	 */

	/*
	 * @RequestMapping(method=RequestMethod.POST) public String salvar(Titulo
	 * titulo){
	 * System.out.println(">>>>"+titulo.getDataVencimento().toString());
	 * System.out.println(">>>>"+titulo.getDescricao());
	 * System.out.println(">>>>"+titulo.getValor()); titulos.save(titulo);
	 * return "CadastroTitulo"; }
	 */
}
