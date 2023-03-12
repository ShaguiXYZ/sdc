import { AppUrls } from 'src/app/shared/config/routing';

export const DEFAULT_HEADER_MENU: INavigation = {
  routes: []
};

export interface INavItem {
  id: string;
  name: string;
  routerLink?: AppUrls;
}

export interface INavHeaderItem extends INavItem {
  collapsed?: boolean;
  children?: INavItem[];
}

export default interface INavigation {
  routes: INavHeaderItem[];
  activeParent?: string; // Id of the parent menu with the focus.
}
