package fc.desafio.tecnico.rest.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fc.desafio.tecnico.rest.domain.entity.Terminal;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Integer>{
	Optional<Terminal> findByLogic(Integer logic);
}
