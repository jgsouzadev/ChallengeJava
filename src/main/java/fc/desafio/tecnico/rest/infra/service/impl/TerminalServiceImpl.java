package fc.desafio.tecnico.rest.infra.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Optional;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;

import fc.desafio.tecnico.rest.domain.entity.Terminal;
import fc.desafio.tecnico.rest.infra.dto.TerminalDTO;
import fc.desafio.tecnico.rest.infra.repository.TerminalRepository;
import fc.desafio.tecnico.rest.infra.service.GetTerminalService;
import fc.desafio.tecnico.rest.infra.service.TerminalService;
import fc.desafio.tecnico.rest.infra.util.ObjectMergeUtil;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class TerminalServiceImpl implements TerminalService {

	static final String[] keys = { "logic", "serial", "model", "sam", "ptid", "plat", "version", "mxr", "mxf",
			"VERFM" };

	static final String JSON_SCHEMA_URL = "BaseJsonSchema.json";

	private final TerminalRepository terminalRepository;

	private final GetTerminalService getService;

	private final ObjectMergeUtil objectUtil;

	@Override
	public void storeTerminal(String text) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject(this.transformRawTextInJSONObject(text));
			Terminal terminal = this.createTerminalObject(jsonObject);
			validarSeJaFoiCadastrado(terminal.getLogic());
			Schema schema = this.loadAndReturnSchema();
			schema.validate(jsonObject);
			terminalRepository.save(terminal);
		} catch (ValidationException e) {
			log.error(e.getLocalizedMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw e;
		}
	}

	@Override
	public Terminal updateTerminal(Integer logic, TerminalDTO terminalDTO)
			throws NotFoundException, IllegalAccessException, InstantiationException {
		Terminal terminal = getService.getTerminalData(logic);

		terminal = objectUtil.mergeObjects(terminalDTO.converterEmModel(terminalDTO), terminal);

		return terminalRepository.save(terminal);
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
		File jsonSchemaFile = new File(JSON_SCHEMA_URL);
		try (InputStream inputStream = new FileInputStream(jsonSchemaFile)) {
			log.info(jsonSchemaFile.getAbsolutePath());
			JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
			schema = SchemaLoader.load(rawSchema);
		} catch (Exception e) {
			log.info(e.getMessage());
			throw e;
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

	private void validarSeJaFoiCadastrado(Integer logic) {
		Optional<Terminal> terminal = terminalRepository.findByLogic(logic);

		if (terminal.isPresent())
			throw new IllegalArgumentException("Já existe um Terminal cadastrado com esse logic");
	}

}
