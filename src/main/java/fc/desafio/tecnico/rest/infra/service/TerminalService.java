package fc.desafio.tecnico.rest.infra.service;

import java.io.IOException;

import org.everit.json.schema.ValidationException;
import org.json.JSONException;
import org.springframework.stereotype.Service;

@Service
public interface TerminalService {
	void getTerminalData(Integer logic);
	
	void storeTerminal(String text) throws Exception;
	
	void updateTerminal(Integer logic, String newData);
}
