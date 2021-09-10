package fc.desafio.tecnico.rest.infra.service;

import org.springframework.stereotype.Service;

@Service
public interface TerminalService {

	void storeTerminal(String text) throws Exception;
	
	void updateTerminal(Integer logic, TerminalDTO terminalDTO);
}
