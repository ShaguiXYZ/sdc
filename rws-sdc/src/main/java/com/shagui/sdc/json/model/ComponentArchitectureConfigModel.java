package com.shagui.sdc.json.model;

import java.util.List;
import java.util.Optional;

import com.shagui.sdc.model.ComponentTypeArchitectureModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentArchitectureConfigModel {
    private List<MetricDefModel> defaultDef;
    private List<MetricArchitecturesDefModel> definitions;

    public Optional<List<MetricDefModel>> metrics(ComponentTypeArchitectureModel model) {
        return this.metrics(model.getComponentType(), model.getArchitecture(), model.getNetwork(),
                model.getDeploymentType(), model.getPlatform(), model.getLanguage());
    }

    private Optional<List<MetricDefModel>> metrics(String componentType, String architecture, String network,
            String deploymentType, String platform, String language) {
        return definitions.stream()
                .filter(def -> def.getComponentType().equalsIgnoreCase(componentType))
                .filter(def -> def.getArchitecture().equalsIgnoreCase(architecture))
                .filter(def -> def.getNetwork().equalsIgnoreCase(network))
                .filter(def -> def.getDeploymentType().equalsIgnoreCase(deploymentType))
                .filter(def -> def.getPlatform().equalsIgnoreCase(platform))
                .filter(def -> def.getLanguage().equalsIgnoreCase(language))
                .map(MetricArchitecturesDefModel::getMetrics)
                .findFirst();
    }
}
