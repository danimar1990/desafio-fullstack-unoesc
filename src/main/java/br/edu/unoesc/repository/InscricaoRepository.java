package br.edu.unoesc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.unoesc.models.Inscricao;
import br.edu.unoesc.models.Usuario;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
  
    Inscricao findByUsuario(Usuario usuario);

    @Query("SELECT i FROM Inscricao i WHERE curso_id=?1")
    List<Inscricao> findByIdCurso(Long id);

    @Query("SELECT i FROM Inscricao i WHERE usuario_id=?1")
    List<Inscricao> findByIdUsuario(Long id);    
}
