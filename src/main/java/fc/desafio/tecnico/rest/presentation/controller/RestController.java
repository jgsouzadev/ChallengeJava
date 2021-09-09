package fc.desafio.tecnico.rest.presentation.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(path = "${env.api.version}/terminal")
public class RestController {
		
	@PostMapping(consumes = MediaType.TEXT_HTML_VALUE)
	public void endpoint(@RequestBody String data) {
		System.out.println(data);
	}
	
	
}
