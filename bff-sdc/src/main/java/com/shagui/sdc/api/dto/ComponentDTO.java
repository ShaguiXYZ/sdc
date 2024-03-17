package com.shagui.sdc.api.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

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
	private Float trend;
	private boolean blocked;
	private SquadDTO squad;

	public ComponentDTO(@NonNull ComponentView source) {
		BeanUtils.copyProperties(source, this);

		this.squad = CastFactory.getInstance(SquadDTO.class).parse(source.getSquad());
	}
}
