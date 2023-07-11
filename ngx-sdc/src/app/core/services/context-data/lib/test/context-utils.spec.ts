import { TestBed } from '@angular/core/testing';
import { Route, Router } from '@angular/router';
import { configContextRoutes, routerData } from '..';
import { RouterInfo } from '../../models';

describe('ContextUtils', () => {
  it('should add a route when configContextRoutes is called', () => {
    const routes: Route[] = [];
    configContextRoutes(routes);
    expect(routes.length).toBe(1);
  });

  it('should get RouterInfo when routerData is called', () => {
    const result: RouterInfo = routerData(TestBed.inject(Router), {
      home: '',
      urls: {}
    });
    expect(result).toBeDefined();
  });
});
