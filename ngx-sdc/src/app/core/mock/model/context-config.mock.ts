import { RouteConfig } from '../../services/context-data';

export class ContextConfigMock {
  home = '';
  urls: RouteConfig = {
    test: { resetContext: true, requiredData: ['test'] }
  };
}
