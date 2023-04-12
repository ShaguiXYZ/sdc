import { AppUrls } from './routing';

export const SDC_HEADER_MENU = {
  routes: [
    {
      id: '1',
      name: 'Header.Menu.Summary',
      routerLink: AppUrls.summary
    },
    {
      id: '2',
      name: 'Header.Menu.Applications',
      collapsed: true,
      children: [
        {
          id: '2@1',
          name: 'Header.Test.1',
          routerLink: AppUrls.applications
        }
      ]
    }
  ],
  activeParent: ''
};
