import { INavigation } from 'src/app/core/components';
import { AppUrls } from './routing';

export const SDC_HEADER_MENU: INavigation = {
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
      roles: ['ADMIN'],
      routerLink: AppUrls.applications
    }
    // {
    //   id: 'test',
    //   name: 'Header.Menu.TestPage',
    //   collapsed: true,
    //   children: [
    //     {
    //       id: 'test@1',
    //       name: 'Header.Test.Components',
    //       routerLink: AppUrls.test
    //     },
    //     {
    //       id: 'test@2',
    //       name: 'Header.Test.2'
    //     }
    //   ]
    // }
  ],
  activeParent: ''
};
