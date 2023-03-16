import { AppUrls } from 'src/app/shared/config/routing';

export const DEFAULT_HEADER_MENU: INavigation = {
  routes: []
};

export interface INavigationItem {
  id: string;
  name: string;
  routerLink?: AppUrls;
}

export interface INavHeaderItem extends INavigationItem {
  children?: INavigationItem[];
  collapsed?: boolean;
}

export interface INavigation {
  activeParent?: string; // Id of the parent menu with the focus.
  routes: INavHeaderItem[];
}
