package com.shagui.sdc.util.git;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.shagui.sdc.api.client.GitClient;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GitUtilsConfig {
	private GitClient gitClient;

	@PostConstruct
	public void init() {
		GitUtils.setConfig(this);
	}
	
	public GitClient gitClient() {
		return gitClient;
	}

}
