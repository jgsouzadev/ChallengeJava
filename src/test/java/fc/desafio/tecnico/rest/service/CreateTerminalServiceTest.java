package fc.desafio.tecnico.rest.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import fc.desafio.tecnico.rest.domain.entity.Terminal;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateTerminalServiceTest {

	static final String[] keys = {"logic", "serial", "model", "sam", "ptid", "plat", "version", "mxr", "mxf", "verfm"};
	
	@Test
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
	

}
