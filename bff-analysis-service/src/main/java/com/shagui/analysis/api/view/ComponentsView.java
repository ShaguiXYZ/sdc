package com.shagui.analysis.api.view;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComponentsView {
	private PagingView paging;
	private List<ComponentView> components;
}
