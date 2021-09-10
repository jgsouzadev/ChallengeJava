package fc.desafio.tecnico.rest.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import fc.desafio.tecnico.rest.domain.entity.Terminal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TerminalDTO {

	private String serial;
	private String model;
	private Integer sam;
	private String ptid;
	private Integer plat;
	private String version;
	private Integer mxr;
	private Integer mxf;
	@JsonProperty(value = "VERMF")
	private String vermf;

	public Terminal converterEmModel(TerminalDTO terminalDTO) {
		return Terminal.builder().withSerial(terminalDTO.getSerial()).withModel(terminalDTO.getModel())
				.withMxf(terminalDTO.getMxf()).withMxr(terminalDTO.getMxr()).withPlat(terminalDTO.getPlat())
				.withPtid(terminalDTO.getPtid()).withVersion(terminalDTO.getVersion()).withVermf(terminalDTO.getVermf())
				.withSam(terminalDTO.getSam()).build();
	}
}
