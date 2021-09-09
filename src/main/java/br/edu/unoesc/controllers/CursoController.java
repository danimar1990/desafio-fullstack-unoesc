package br.edu.unoesc.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.unoesc.models.Curso;
import br.edu.unoesc.models.Disciplina;
import br.edu.unoesc.models.Inscricao;
import br.edu.unoesc.repository.CursoRepository;
import br.edu.unoesc.repository.DisciplinaRepository;
import br.edu.unoesc.repository.InscricaoRepository;
import br.edu.unoesc.repository.UsuarioRepository;

@Controller
@RequestMapping("curso")
public class CursoController {

    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    DisciplinaRepository disciplinaRepository;    
    @Autowired
    InscricaoRepository inscricaoRepository;
        
    @GetMapping("/") 
    public String listarCursos(Model model){
        List<Curso> cursos = cursoRepository.findAll();
        model.addAttribute("cursos", cursos);
        return "curso/listCurso";
    }
    
    @GetMapping("/new")
    public String cursoCadastro(Model model){
        List<Disciplina> listaDisciplinas = disciplinaRepository.findAll();
        model.addAttribute("curso", new Curso());
        model.addAttribute("listaDisciplinas", listaDisciplinas);

        return "curso/formCurso";
    }    

    @PostMapping("/save")
    public String saveCurso(@Valid Curso curso, BindingResult result, Model model, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message", "Erro ao cadastrar o curso!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger"); 
        if(result.hasErrors()){
            List<Disciplina> listaDisciplinas = disciplinaRepository.findAll();
            model.addAttribute("listaDisciplinas", listaDisciplinas);
            model.addAttribute("curso", curso);
            return "/curso/formCurso";    
        }       
        String operacao = curso.getId() == null ? "cadastrado" : "alterado";
        redirectAttributes.addFlashAttribute("message", "Curso "+operacao+" com sucesso!");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");  
        cursoRepository.save(curso);     
        return "redirect:/curso/";
    }

    @GetMapping("/edit/{id}")
    public String showEditCursoForm(@PathVariable("id") Long id, Model model){          
        Curso curso = cursoRepository.findById(id).get(); 
        List<Disciplina> listaDisciplinas = disciplinaRepository.findAll();        
        model.addAttribute("curso", curso);
        model.addAttribute("listaDisciplinas", listaDisciplinas);
        return "/curso/formCurso";
    } 

    @GetMapping("/delete/{id}")
    public String deleteCurso(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){      

        List<Inscricao> listaInscricoesPorCurso = inscricaoRepository.findByIdCurso(id);

        if(listaInscricoesPorCurso.size()> 0){
            redirectAttributes.addFlashAttribute("message", "Este curso não pode ser excluido pois já existem inscrições vinculadas a ele!");
        }
        else{
            redirectAttributes.addFlashAttribute("message", "Curso excluído com sucesso!");
            cursoRepository.deleteById(id);
        }
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");           
        return "redirect:/curso/";
    }     
    
}
