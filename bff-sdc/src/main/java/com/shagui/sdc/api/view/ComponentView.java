package com.shagui.sdc.api.view;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.shagui.sdc.api.domain.CastFactory;
import com.shagui.sdc.api.dto.ComponentDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComponentView {
	private Integer id;
	private String name;
	private Date analysisDate;
	private Float coverage;
	private Float trend;
	private boolean blocked;
	private SquadView squad;

	public ComponentView(ComponentDTO source) {
		BeanUtils.copyProperties(source, this);

		this.squad = CastFactory.getInstance(SquadView.class).parse(source.getSquad());
	}
}
