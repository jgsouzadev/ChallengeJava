package fc.desafio.tecnico.rest.infra.service.impl;

import org.springframework.stereotype.Service;

import fc.desafio.tecnico.rest.domain.entity.Terminal;
import fc.desafio.tecnico.rest.infra.repository.TerminalRepository;
import fc.desafio.tecnico.rest.infra.service.GetTerminalService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GetTerminalServiceImpl implements GetTerminalService {

	private final TerminalRepository terminalRepository;

	@Override
	public Terminal getTerminalData(Integer logic) throws NotFoundException {
		return terminalRepository.findByLogic(logic).orElseThrow(
				() -> new NotFoundException("Não foi encontrado algum terminal com esse logic"));
	}

}
