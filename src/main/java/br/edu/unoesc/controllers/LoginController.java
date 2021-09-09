package br.edu.unoesc.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.unoesc.dto.LoginDto;
import br.edu.unoesc.models.Usuario;
import br.edu.unoesc.repository.UsuarioRepository;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login/sing-in")
    public String singIn(@Valid LoginDto loginDto, BindingResult result, RedirectAttributes redirectAttributes) {        
        if (result.hasErrors()) {
            return "login";
        }        
        String acesso = "";
        Usuario user  = userRepository.findByUsuario(loginDto.getUsuario());
        if (user != null) {
            if (user.getSenha().equals(loginDto.getSenha())) {
                acesso = "redirect:/home/index";
            } else {
                redirectAttributes.addFlashAttribute("message", "Verifique sua senha e tente novamente!");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                redirectAttributes.addFlashAttribute(loginDto);                
                acesso = "redirect:/login";
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "O usuário informado não existe!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute(loginDto);             
            acesso = "redirect:/login";
        }
        return acesso;
    }

}
