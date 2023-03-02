import { Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { HttpStatus } from 'src/app/shared/config/app.constants';
import { environment } from 'src/environments/environment';
import { UiHttpHelper } from '.';
import {
  IComponentStateDTO,
  IComponentStateModel
} from '../models';

@Injectable({ providedIn: 'root' })
export class ComponentService {
  private _urlComponents = `${environment.baseUrl}/component`;

  constructor(private http: UiHttpHelper) {}

  public componentState(componentId: number): Promise<IComponentStateModel> {
    return firstValueFrom(
      this.http
        .get<IComponentStateDTO>(`${this._urlComponents}/${componentId}/state`, {
          responseStatusMessage: {
            [HttpStatus.notFound]: { message: 'Notifications.ComonentNotFound' }
          }
        })
        .pipe(
          map(state => {
            return IComponentStateModel.toModel(state as IComponentStateDTO);
          })
        )
    );
  }
}
