package com.shagui.sdc.core.configuration.dto;

import com.shagui.sdc.util.Ctes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JpaConfig {
    private int elementsByPage;

    public JpaConfig() {
        this.elementsByPage = Ctes.JPA.ELEMENTS_BY_PAGE;
    }
}
