package fc.desafio.tecnico.rest.infra.exception;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.everit.json.schema.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javassist.NotFoundException;

@ControllerAdvice
class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = NotFoundException.class)
	protected ResponseEntity<CustomThrowableErrorBody> handleInternalServerError(NotFoundException e,
			WebRequest request) {
		CustomThrowableErrorBody message = new CustomThrowableErrorBody("Não foi encontrado", e.getLocalizedMessage(),
				HttpStatus.BAD_REQUEST);
		return new ResponseEntity<CustomThrowableErrorBody>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = IOException.class)
	protected ResponseEntity<CustomThrowableErrorBody> handleInternalServerError(IOException e, WebRequest request) {
		CustomThrowableErrorBody message = new CustomThrowableErrorBody("Erro na leitura dos arquivos",
				e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<CustomThrowableErrorBody>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	protected ResponseEntity<CustomThrowableErrorBody> handleInternalServerError(IllegalArgumentException e,
			WebRequest request) {
		CustomThrowableErrorBody message = new CustomThrowableErrorBody("Erro ao validar", e.getLocalizedMessage(),
				HttpStatus.BAD_REQUEST);
		return new ResponseEntity<CustomThrowableErrorBody>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = Exception.class)
	protected ResponseEntity<CustomThrowableErrorBody> handleInternalServerError(Exception e, WebRequest request) {
		CustomThrowableErrorBody message = new CustomThrowableErrorBody("Erro não tratado pelo servidor",
				e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<CustomThrowableErrorBody>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ValidationException.class)
	protected ResponseEntity<CustomThrowableErrorBody> handleInternalServerError(ValidationException e,
			WebRequest request) {
		CustomThrowableErrorBody message = new CustomThrowableErrorBody("Erro na validação", concatValidationExceptionMessages(e.getAllMessages()),
				HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<CustomThrowableErrorBody>(message, HttpStatus.BAD_REQUEST);
	}

	private String concatValidationExceptionMessages(List<String> strings ) {
		Optional<String> start = strings.stream().reduce(String::concat);
		return start.isPresent() ? start.get() : "Erro ao validar SCHEMA";
	}

}