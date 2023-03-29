package com.shagui.sdc.api.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.view.ComponentView;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentDTO {
	private Integer id;
	private String name;
	private Date analysisDate;
	private Float coverage;
	private ComponentTypeDTO componentType;
	private ArchitectureDTO architecture;
	private SquadDTO squad;

	public ComponentDTO(ComponentView source) {
		BeanUtils.copyProperties(source, this);

		this.componentType = CastFactory.getInstance(ComponentTypeDTO.class).parse(source.getComponentType());
		this.architecture = CastFactory.getInstance(ArchitectureDTO.class).parse(source.getArchitecture());
		this.squad = CastFactory.getInstance(SquadDTO.class).parse(source.getSquad());
	}
}
