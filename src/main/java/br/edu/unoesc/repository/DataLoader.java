package br.edu.unoesc.repository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.edu.unoesc.models.Perfil;
import br.edu.unoesc.models.Usuario;

@Component
public class DataLoader {

    private UsuarioRepository userRepository;
    private PerfilRepository perfilRepository;

    @Autowired
    public DataLoader(UsuarioRepository userRepository, PerfilRepository perfilRepository) {
        this.userRepository = userRepository;
        this.perfilRepository = perfilRepository;
        LoadPerfils();
        LoadUsers();
    }

    private void LoadUsers() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Set<Perfil> perfils = new HashSet<Perfil>();
        perfils.add(perfilRepository.getOne(Long.valueOf(1L)));
        userRepository.save(new Usuario(Long.valueOf(1L), 
                                        "Administrador", "admin", 
                                        encoder.encode("admin"), 
                                        perfils)
                            );
    }

    private void LoadPerfils(){
        perfilRepository.save(new Perfil(Long.valueOf(1L), "ADMIN"));
        perfilRepository.save(new Perfil(Long.valueOf(2L), "PROFESSOR"));
        perfilRepository.save(new Perfil(Long.valueOf(3L), "ALUNO"));    
    }
}
