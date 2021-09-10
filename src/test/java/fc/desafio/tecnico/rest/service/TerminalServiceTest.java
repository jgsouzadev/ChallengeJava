package fc.desafio.tecnico.rest.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.assertj.core.api.Assert;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fc.desafio.tecnico.rest.domain.entity.Terminal;
import fc.desafio.tecnico.rest.infra.dto.TerminalDTO;
import fc.desafio.tecnico.rest.infra.repository.TerminalRepository;
import fc.desafio.tecnico.rest.infra.service.GetTerminalService;
import fc.desafio.tecnico.rest.infra.service.TerminalService;
import javassist.NotFoundException;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TerminalServiceTest {

	static final String[] keys = {"logic", "serial", "model", "sam", "ptid", "plat", "version", "mxr", "mxf", "verfm"};
	
	@Autowired
	TerminalService service;
	
	@Autowired
	GetTerminalService getService;
	
	@Autowired
	TerminalRepository repository;
	
	@AfterAll
	void clean() {
		repository.deleteAll();
	}
	
	@Test
	@Order(1)
	void shouldBeReceiveATerminalAndValid() throws IOException, ValidationException {
		String textOrig = "44332211;'123';PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN";

		List<Integer> stringNumbers = new ArrayList<>();
		
		stringNumbers.add(1);
		
		String[] textArray = textOrig.split(";");
		
		String rawObject = "";
		
		for (int i = 0; i < textArray.length; i++) {
			String texto = String.format(",%s:%s", keys[i], textArray[i]);
			rawObject = rawObject + texto;
		}
		
		String rawJsonObject = String.format("{%s}", rawObject.substring(1));
		System.out.println(rawJsonObject);
		JSONObject jsonObject = new JSONObject(rawJsonObject);
		System.out.println(jsonObject);
		
	
		Assertions.assertDoesNotThrow( () -> {
			Schema schema = null;
			try (InputStream inputStream = getClass().getResourceAsStream("BaseJsonSchema.json")) {
				JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
				schema = SchemaLoader.load(rawSchema);
			} 
			schema.validate(jsonObject);	
		});
	}
	
	@Test
	@Order(2)
	void shouldBeTransformValidTerminalInEntity() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, JSONException {
		String validRaw= "{logic:44332211,serial:'123',model:PWWIN,ptid:F04A2E4088B,plat:4,version:8.00b3,mxr:0,mxf:16777216,verfm:PWWIN}";
		JSONObject jsonObject = new JSONObject(validRaw);
		
		Terminal terminal = new Terminal();
		Field[] modelFields = terminal.getClass().getDeclaredFields();
		
		for (int i = 0; i < modelFields.length; i++) {
			if(jsonObject.has(keys[i])) {
				modelFields[i].setAccessible(true);
				System.out.println(modelFields[i].getName());
				modelFields[i].set(terminal, jsonObject.get(keys[i]));
				modelFields[i].setAccessible(false);	
			}
		}
		System.out.println(terminal);
		
		Assertions.assertEquals(terminal.getLogic(), 44332211);
	}
	
	
	@Test
	@Order(3)
 	void shouldBeCreateATerminal() throws Exception {
		String raw = "44332222;'123';PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN";
		service.storeTerminal(raw);
		Terminal terminal = getService.getTerminalData(44332211);
		Assertions.assertNotNull(terminal);
		
		
	}

	@Test
	@Order(4)
 	void shouldBeUpdateATerminal() throws Exception {
		
		String raw = "44332211;'123';PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN";
		service.storeTerminal(raw);
		
		TerminalDTO dto = TerminalDTO.builder()
		.model("MODEL_ALTERADO")
		.build();
		
		service.updateTerminal(44332211, dto);
		Terminal terminal = getService.getTerminalData(44332211);
		Assertions.assertEquals("MODEL_ALTERADO", terminal.getModel());
		
	}
	
	@Test
	@Order(5)
	void shouldThrowExceptionOnTryCreateATerminalStoredBefore() throws Exception {
		String raw = "552233;'123';PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN";
		service.storeTerminal(raw);
		Assertions.assertThrows(IllegalArgumentException.class, () -> service.storeTerminal(raw));
	}
	
	@Test
	@Order(6)
	void shouldThrowExceptionOnTrySearchNotFoundTerminal() throws Exception {
		Assertions.assertThrows(NotFoundException.class, () -> getService.getTerminalData(48125));
	}
}
