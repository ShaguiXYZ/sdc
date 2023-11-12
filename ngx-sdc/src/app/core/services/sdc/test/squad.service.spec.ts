import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { IPageable, ISquadDTO } from 'src/app/core/models/sdc';
import { SquadService } from '../squad.service';
import { HttpService } from '../../http';
import { HttpServiceMock } from 'src/app/core/mock/services/http-service.mock';

const pageSquad: IPageable<ISquadDTO> = {
  paging: { pageIndex: 0, pageSize: 1, pages: 1, elements: 1 },
  page: [{ department: { id: 1, name: '' }, id: 1, name: '' }]
};

let service: SquadService;
let services: any;
let spies: any;

describe('SquadService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: HttpService, useClass: HttpServiceMock }]
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

  const initServices = () => {
    services = {
      http: TestBed.inject(HttpService)
    };
    initSpies();
  };

  const initSpies = () => {
    spies = {
      http: {
        get: spyOn(services.http, 'get')
      }
    };
  };
});
