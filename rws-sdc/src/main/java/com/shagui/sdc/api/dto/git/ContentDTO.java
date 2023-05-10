package com.shagui.sdc.api.dto.git;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentDTO {
	private String name;
	private String path;
	private String sha;
	private long size;
	private String url;
	@JsonProperty("html_url")
	private String htmlUrl;
	@JsonProperty("download_url")
	private String downloadUrl;
	private String type;
	private String content;
	private String encoding;
}
