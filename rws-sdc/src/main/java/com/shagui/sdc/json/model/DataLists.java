package com.shagui.sdc.json.model;

public enum DataLists {
    ARCHITECTURES("architectures"),
    COMPONENT_TYPES("component_types"),
    DEPLOYMENT_TYPES("deployment_types"),
    NETWORKS("networks"),
    PLATFORMS("platforms"),
    LANGUAGES("languages");

    private final String name;

    DataLists(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
