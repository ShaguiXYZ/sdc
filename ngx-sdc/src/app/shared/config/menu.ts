import { AppUrls } from './routing';

export const SDC_HEADER_MENU = {
  routes: [
    {
      id: 'sdc_home',
      name: 'Header.Menu.Summary',
      routerLink: AppUrls.summary
    },
    {
      id: 'sdc_home',
      name: 'Header.Menu.Applications',
      collapsed: true,
      children: [
        {
          id: 'sdc_home@1',
          name: 'Header.Test.1',
          routerLink: AppUrls.applications
        }
      ]
    }
  ],
  activeParent: ''
};
