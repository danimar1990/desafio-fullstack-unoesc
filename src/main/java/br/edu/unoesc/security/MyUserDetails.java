package br.edu.unoesc.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import br.edu.unoesc.models.Perfil;
import br.edu.unoesc.models.Usuario;

public class MyUserDetails implements UserDetails{

    private static final long serialVersionUID = 1L;

    private Usuario user;
     
    public MyUserDetails(Usuario user) {
        this.user = user;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Perfil> perfils = user.getPerfils();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        for (Perfil perfil : perfils) {
            authorities.add(new SimpleGrantedAuthority(perfil.getTipo()));
        }
         
        return authorities;
    }
 
    @Override
    public String getPassword() {
        return user.getSenha();
    }
 
    @Override
    public String getUsername() {
        return user.getUsuario();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }    
    
}
