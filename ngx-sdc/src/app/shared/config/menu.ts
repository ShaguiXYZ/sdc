import { AppUrls } from './routing';

export const SDC_HEADER_MENU = {
  routes: [
    {
      id: '1',
      name: 'Header.Menu.Departments',
      routerLink: AppUrls.departments
    },
    {
      id: '2',
      name: 'Header.Menu.Squads',
      routerLink: AppUrls.squads
    },
    {
      id: '3',
      name: 'Header.Menu.Applications',
      collapsed: true,
      routerLink: AppUrls.applications
      // children: [
      //   {
      //     id: '3@1',
      //     name: 'Header.Test.1',
      //   }
      // ]
    },
    {
      id: 'test',
      name: 'Header.Menu.TestPage',
      collapsed: true,
      routerLink: AppUrls.test
    }
  ],
  activeParent: ''
};
