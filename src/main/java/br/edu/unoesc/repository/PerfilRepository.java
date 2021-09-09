package br.edu.unoesc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.edu.unoesc.models.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{
    
}
