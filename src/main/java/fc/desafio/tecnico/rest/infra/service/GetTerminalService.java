package fc.desafio.tecnico.rest.infra.service;

import org.springframework.stereotype.Service;

import fc.desafio.tecnico.rest.domain.entity.Terminal;
import javassist.NotFoundException;

@Service
public interface GetTerminalService {
	
	Terminal getTerminalData(Integer logic) throws NotFoundException;
	
}
