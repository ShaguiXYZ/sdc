export const DEFAULT_HEADER_MENU: INavigation = {
  routes: []
};

export interface INavigationItem {
  id: string;
  name: string;
  roles?: string[];
  routerLink?: string;
}

export interface INavHeaderItem extends INavigationItem {
  children?: INavigationItem[];
  collapsed?: boolean;
}

export interface INavigation {
  activeParent?: string; // Id of the parent menu with the focus.
  routes: INavHeaderItem[];
}
