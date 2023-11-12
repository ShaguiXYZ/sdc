import { ComponentProperty } from './component-property.model';

export interface ComponentFormData {
  name: string;
  typeArchitecture: {
    componentType: string;
    network: string;
    deploymentType: string;
    platform: string;
    architecture: string;
    language: string;
  };
  squad: string;
  properties: ComponentProperty[];
  uriNames: string[];
}
