/* eslint max-classes-per-file: 0 */
import { TestBed } from '@angular/core/testing';
import { UiHttpService } from '../http';
import { of } from 'rxjs';
import { IPageable, ISquadDTO } from '../../models/sdc';
import { SquadService } from './squad.service';


let pageSquad: IPageable<ISquadDTO> = { paging: { pageIndex: 0, pageSize: 1, pages: 1, elements: 1 }, page: [{ department: { id: 1, name: '' }, id: 1, name: '' }] };

let service: SquadService;
let services: any, spies: any;

describe('SquadService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: UiHttpService, useClass: MockUiHttpService }]
    });
    service = TestBed.inject(SquadService);
    initServices();
  });

  it('should create service', () => {
    expect(service).toBeTruthy();
  });

  it('should call http get when analysis is called', async () => {
    spies.http.get.and.returnValue(of(pageSquad));
    await service.squads({ id: 1, name: '' });
    expect(spies.http.get).toHaveBeenCalled();
  });

  function initServices() {
    services = {
      http: TestBed.inject(UiHttpService)
    };
    initSpies();
  }

  function initSpies() {
    spies = {
      http: {
        get: spyOn(services.http, 'get')
      }
    };
  }
});

class MockUiHttpService {
  get() { }
}
