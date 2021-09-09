package fc.desafio.tecnico.rest.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.ValidationException;

import org.assertj.core.util.Arrays;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TerminalServiceTest {

	static final String[] keys = {"logic", "serial", "model", "sam", "ptid", "plat", "version", "mxr", "mxf", "VERFM"};
	
	@Test
	void shouldBeReceiveATerminalAndSave() throws IOException, ValidationException {
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

}
