package fc.desafio.tecnico.rest.infra.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import fc.desafio.tecnico.rest.domain.entity.Terminal;
import fc.desafio.tecnico.rest.infra.repository.TerminalRepository;
import fc.desafio.tecnico.rest.infra.service.GetTerminalService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GetTerminalServiceImpl implements GetTerminalService {

	private final TerminalRepository terminalRepository;

	@Override
	public Terminal getTerminalData(Integer logic) throws NotFoundException {
		Optional<Terminal> terminal = terminalRepository.findByLogic(logic);

		if (terminal.isPresent()) {
			return terminal.get();
		} else {
			throw new NotFoundException("Não foi encontrado algum terminal com esse logic");
		}

	}

}
