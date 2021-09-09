package fc.desafio.tecnico.rest.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fc.desafio.tecnico.rest.domain.entity.Terminal;

@Repository
interface TerminalRepository extends JpaRepository<Terminal, Integer>{

}
