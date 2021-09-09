package br.edu.unoesc.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import br.edu.unoesc.dto.PerfilDto;
import br.edu.unoesc.dto.UsuarioDto;

@Entity
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String usuario;
    private String senha;

    @OneToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name="professor_id")
    List<Disciplina> disciplinas = new ArrayList<>();    

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_perfil",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    private Set<Perfil> perfils = new HashSet<>();    

    public Usuario(Long id, String nome, String usuario, String senha, Set<Perfil> perfils) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.perfils = perfils;
    }
    
    public Usuario(){}

    public Boolean isProfessor(){
        Boolean result = false;
        for(Perfil perfil : perfils){
            result = perfil.getTipo().equals("PROFESSOR");
        }
        return result;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsuarioDto toUsuarioDto(){
        UsuarioDto userDto = new UsuarioDto();
        userDto.setUsuario(usuario);
        userDto.setNome(nome);
        userDto.setSenha(senha);
        userDto.setId(id);
        userDto.setPerfilsDto(prepararSetPerfilsDto());
        return userDto;
    }

    Set<PerfilDto>prepararSetPerfilsDto(){
        Set<PerfilDto> perfilsDto = new HashSet<PerfilDto>();
        for(Perfil perfil : this.perfils){
            perfilsDto.add(perfil.toPerfilDto());
        }
        return perfilsDto;
    }

    public Set<Perfil> getPerfils() {
        return perfils;
    }
    public void setPerfils(Set<Perfil> perfil) {
        this.perfils = perfil;
    }

    public void setPerfils(Optional<Perfil> perfil) {
        perfils.add(perfil.get());
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

}
