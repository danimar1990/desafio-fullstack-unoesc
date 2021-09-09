package br.edu.unoesc.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.unoesc.dto.UsuarioDto;
import br.edu.unoesc.models.Inscricao;
import br.edu.unoesc.models.Perfil;
import br.edu.unoesc.models.Usuario;
import br.edu.unoesc.repository.InscricaoRepository;
import br.edu.unoesc.repository.PerfilRepository;
import br.edu.unoesc.repository.UsuarioRepository;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PerfilRepository perfilRepository;    

    @Autowired
    InscricaoRepository inscricaoRepository;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("cadastro")
    public String user_cad(UsuarioDto userDto, Model model, BindingResult result){
        List<Perfil> listaPerfils = perfilRepository.findAll();

        model.addAttribute("listaPerfils", listaPerfils);
        return "usuario/formUsuario";
    }

    @GetMapping("/cadastro/{id}")
    public String user_cad_edicao(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message", "Erro ao cadastrar usuário!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        Usuario user = userRepository.getOne(id); 
        model.addAttribute("usuarioDto", user.toUsuarioDto());
        List<Perfil> listaPerfils = perfilRepository.findAll();
        model.addAttribute("listaPerfils",  listaPerfils); 
        redirectAttributes.addFlashAttribute("message", "Usuário alterado com sucesso!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");          
        return "/usuario/formUsuario";
    }

    @PostMapping("/novo")
    public String novo(@Valid UsuarioDto userDto, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        redirectAttributes.addFlashAttribute("message", "Erro ao cadastrar usuário!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        redirectAttributes.addFlashAttribute(userDto);
        if(result.hasErrors()){
            List<Perfil> listaPerfils = perfilRepository.findAll();
            model.addAttribute("listaPerfils", listaPerfils);            
            model.addAttribute("userDto", userDto);
            return "usuario/formUsuario";    
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode(userDto.getSenha());
        userDto.setSenha(senhaCriptografada);

        Usuario user = userDto.toUsuario();
        Optional<Perfil> perfil;
        if(userDto.getId_perfil() > 0){
            perfil = perfilRepository.findById(Long.valueOf(userDto.getId_perfil()));
        } else {
            perfil = perfilRepository.findById(Long.valueOf(3L));
        }  
        user.setPerfils(perfil);
        String opcao = userDto.getId() == null ? "cadastrado" : "alterado";  
        redirectAttributes.addFlashAttribute("message", "Usuário "+opcao+" com sucesso!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        userRepository.save(user);
        redirectAttributes.addFlashAttribute(user.toUsuarioDto());
        
        if(usuarioService.userIsAnonymous()){
            redirectAttributes.addFlashAttribute("message", "Usuário criado com sucesso!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            return "redirect:/login";
        }
        return "redirect:/usuario/";            
    }

    @GetMapping("/")
    public String listarUsuarios(Model model){
        List<Usuario> usuarios = userRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuario/listUsuario";
    }

    @GetMapping("/delete/{id}")
    public String excluirUsuario(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){  
        List<Inscricao> listaInscricoesPorCurso = inscricaoRepository.findByIdUsuario(id);
        if(listaInscricoesPorCurso.size()> 0){
            redirectAttributes.addFlashAttribute("message", "Este usuário não pode ser excluído pois está inscrito em um curso!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Usuário excluído com sucesso!");
            userRepository.deleteById(id);    
        }
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");           
        return "redirect:/usuario/";
    }

}
