package fc.desafio.tecnico.rest.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import fc.desafio.tecnico.rest.domain.entity.Terminal;
import fc.desafio.tecnico.rest.infra.dto.TerminalDTO;
import fc.desafio.tecnico.rest.infra.service.GetTerminalService;
import fc.desafio.tecnico.rest.infra.service.TerminalService;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(path = "${env.api.version}/terminal")
@AllArgsConstructor
public class RestController {

	final TerminalService terminalService;

	final GetTerminalService getTerminalService;

	@PostMapping(consumes = MediaType.TEXT_HTML_VALUE)
	@ApiOperation(value = "Criar novo Terminal")
	public ResponseEntity<?> createNewTerminal(@RequestBody String data) throws Exception {
		terminalService.storeTerminal(data);
		return ResponseEntity.status(201).build();
	}

	@GetMapping("/{logic}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Recuperar um Terminal")
	public ResponseEntity<Terminal> getTerminal(@PathVariable Integer logic) throws NotFoundException {
		return ResponseEntity.status(200).body(getTerminalService.getTerminalData(logic));
	}

	@PutMapping(path = "/{logic}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Atualizar um terminal")
	public ResponseEntity<Terminal> updateTerminalInfo(@PathVariable Integer logic, @RequestBody TerminalDTO terminalDTO) 
			throws IllegalAccessException, InstantiationException, NotFoundException {
		return ResponseEntity.status(200).body(terminalService.updateTerminal(logic, terminalDTO));
	}

}
