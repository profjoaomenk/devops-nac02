package br.com.fiap.devops.nac02.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.devops.nac02.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	List<Usuario> findByNomeContainsIgnoreCase(String nome);
}
