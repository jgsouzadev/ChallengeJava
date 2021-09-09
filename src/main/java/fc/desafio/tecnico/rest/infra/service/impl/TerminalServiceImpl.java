package fc.desafio.tecnico.rest.infra.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;

import fc.desafio.tecnico.rest.domain.entity.Terminal;
import fc.desafio.tecnico.rest.infra.repository.TerminalRepository;
import fc.desafio.tecnico.rest.infra.service.TerminalService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TerminalServiceImpl implements TerminalService {

	static final String[] keys = { "logic", "serial", "model", "sam", "ptid", "plat", "version", "mxr", "mxf",
			"VERFM" };

	static final String JSON_SCHEMA_URL = "/BaseJsonSchema.json";

	private final TerminalRepository terminalRepository;

	@Override
	public void getTerminalData(Integer logic) {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeTerminal(String text) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject(this.transformRawTextInJSONObject(text));
			Schema schema = this.loadAndReturnSchema();
			schema.validate(jsonObject);
			terminalRepository.save(this.createTerminalObject(jsonObject));
		} catch (Exception e) {
			throw new IllegalArgumentException("Não foi possivel adicionar o Terminal");
		}
	}

	@Override
	public void updateTerminal(Integer logic, String newData) {
		// TODO Auto-generated method stub

	}

	private String transformRawTextInJSONObject(String text) {

		String[] textArray = text.split(";");

		String rawObject = "";

		for (int i = 0; i < textArray.length; i++) {
			String texto = String.format(",%s:%s", keys[i], textArray[i]);
			rawObject = rawObject + texto;
		}

		return String.format("{%s}", rawObject.substring(1));
	}

	private Schema loadAndReturnSchema() throws IOException, ValidationException {
		Schema schema = null;

		try (InputStream inputStream = getClass().getResourceAsStream(JSON_SCHEMA_URL)) {
			JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
			schema = SchemaLoader.load(rawSchema);
		}

		return schema;
	}

	private Terminal createTerminalObject(JSONObject jsonObject)
			throws IllegalArgumentException, IllegalAccessException, JSONException {

		Terminal terminal = new Terminal();
		Field[] modelFields = terminal.getClass().getDeclaredFields();

		for (int i = 0; i < modelFields.length; i++) {
			if (jsonObject.has(keys[i])) {
				modelFields[i].setAccessible(true);
				modelFields[i].set(terminal, jsonObject.get(keys[i]));
				modelFields[i].setAccessible(false);
			}
		}
		return terminal;

	}

}
