package br.edu.unoesc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.unoesc.models.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>{
    
}
