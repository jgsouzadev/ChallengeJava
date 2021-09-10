package fc.desafio.tecnico.rest.presentation.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import fc.desafio.tecnico.rest.domain.entity.Terminal;
import fc.desafio.tecnico.rest.infra.dto.TerminalDTO;
import fc.desafio.tecnico.rest.infra.service.GetTerminalService;
import fc.desafio.tecnico.rest.infra.service.TerminalService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(path = "${env.api.version}/terminal")
@AllArgsConstructor
public class RestController {

	final TerminalService terminalService;
	
	final GetTerminalService getTerminalService;

	@PostMapping(consumes = MediaType.TEXT_HTML_VALUE)
	public void createNewTerminal(@RequestBody String data) {
		try {
			terminalService.storeTerminal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/{logic}")
	public Terminal getTerminal(@PathVariable Integer logic) throws NotFoundException {
		return getTerminalService.getTerminalData(logic);
	}
	
	@PutMapping("/{logic}")
	public void updateTerminalInfo(@PathVariable Integer logic, @RequestBody TerminalDTO terminalDTO) {
		terminalService.updateTerminal(logic, terminalDTO);
	}

}
