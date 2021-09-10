package fc.desafio.tecnico.rest.infra.exception;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CustomThrowableErrorBody implements Serializable {

	private static final long serialVersionUID = 4705874544796944107L;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime timestamp;
	private String mensagem;
	private String detalhes;
	private HttpStatus httpCodigo;

	public CustomThrowableErrorBody(String message, String details, HttpStatus httpCodeMessage) {
	    this.timestamp = LocalDateTime.now();
	    this.mensagem = message;
	    this.detalhes = details;
	    this.httpCodigo=httpCodeMessage;
	  }


}