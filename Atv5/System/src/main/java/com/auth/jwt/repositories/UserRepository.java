package com.auth.jwt.repositories;

import com.auth.jwt.enums.UserRole;
import com.auth.jwt.model.Empresa;
import com.auth.jwt.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);
    List<Usuario> findByEmpresaAndRole(Empresa empresa, UserRole role);
    List<Usuario> findByEmpresaIdAndRole(Long empresaId, UserRole role);
    List<Usuario> findByEmpresaIdAndRoleNot(Long empresaId, UserRole role);
}