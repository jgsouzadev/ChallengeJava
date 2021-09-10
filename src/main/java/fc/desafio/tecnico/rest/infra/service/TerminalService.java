package fc.desafio.tecnico.rest.infra.service;

import org.springframework.stereotype.Service;

import fc.desafio.tecnico.rest.domain.entity.Terminal;
import fc.desafio.tecnico.rest.infra.dto.TerminalDTO;
import javassist.NotFoundException;

@Service
public interface TerminalService {

	void storeTerminal(String text) throws Exception;
	
	Terminal updateTerminal(Integer logic, TerminalDTO terminalDTO) 
			throws NotFoundException, IllegalAccessException, InstantiationException;

}
