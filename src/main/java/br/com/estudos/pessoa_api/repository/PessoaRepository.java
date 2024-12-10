package br.com.estudos.pessoa_api.repository;

import br.com.estudos.pessoa_api.entitie.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
