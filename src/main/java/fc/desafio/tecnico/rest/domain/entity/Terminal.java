package fc.desafio.tecnico.rest.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "TERMINAL")
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class Terminal {

	@Id
	private Integer logic;
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

}
