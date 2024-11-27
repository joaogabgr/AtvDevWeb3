package com.auth.sistema.model;

import com.auth.sistema.enums.PerfilUsuario;
import com.auth.sistema.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "telefones", "documentos", "emails", "credenciais", "mercadorias", "vendas", "veiculos", "endereco" })
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    private String nomeSocial;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<PerfilUsuario> perfis = new HashSet<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Telefone> telefones = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Endereco endereco;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Documento> documentos = new HashSet<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Email> emails = new HashSet<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Credencial> credenciais = new HashSet<>();

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    private Set<Mercadoria> mercadorias = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    private Set<Venda> vendas = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    private Set<Veiculo> veiculos = new HashSet<>();

    public Usuario(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = UserRole.valueOf(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Adiciona permissões para ADMIN
        if (role.equals(UserRole.ADMIN)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_GERENTE"));
            authorities.add(new SimpleGrantedAuthority("ROLE_VENDEDOR"));
            authorities.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        }
        // Adiciona permissões para GERENTE
        else if (role.equals(UserRole.GERENTE)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_GERENTE"));
            authorities.add(new SimpleGrantedAuthority("ROLE_VENDEDOR"));
            authorities.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        }
        // Adiciona permissões para VENDEDOR
        else if (role.equals(UserRole.VENDEDOR)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_VENDEDOR"));
            authorities.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        }
        // Permissões para CLIENTE ou qualquer outro papel
        else {
            authorities.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        }

        return authorities;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
