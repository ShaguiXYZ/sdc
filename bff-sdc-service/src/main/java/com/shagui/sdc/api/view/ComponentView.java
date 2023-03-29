package com.shagui.sdc.api.view;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.dto.ComponentDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentView {
	private Integer id;
	private String name;
	private Date analysisDate;
	private Float coverage;
	private ComponentTypeView componentType;
	private ArchitectureView architecture;
	private SquadView squad;

	public ComponentView(ComponentDTO source) {
		BeanUtils.copyProperties(source, this);
		
		this.componentType = CastFactory.getInstance(ComponentTypeView.class).parse(source.getComponentType());
		this.architecture = CastFactory.getInstance(ArchitectureView.class).parse(source.getArchitecture());
		this.squad = CastFactory.getInstance(SquadView.class).parse(source.getSquad());
	}
}
