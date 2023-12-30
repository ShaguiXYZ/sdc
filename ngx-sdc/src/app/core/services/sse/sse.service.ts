import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CONTEXT_WORKFLOW_ID } from '../security';
import { SseEventDTO, SseEventModel } from './models';

/**
 * (SSE - is a technology where a browser receives automatic updates from a
 * server via HTTP connection)
 *
 * @howto: server-sent events client
 */
@Injectable({ providedIn: 'root' })
export class SseService<T = any> {
  private _urlEvents = `${environment.baseUrl}/api`;

  constructor(private readonly _zone: NgZone) {}

  onEvent(): Observable<SseEventModel<T>> {
    return new Observable<SseEventDTO<T>>(observer => {
      const eventSource = this.getEventSource();

      eventSource.onmessage = event => this._zone.run(() => observer.next(SseEventModel.fromDTO(JSON.parse(event.data))));
      eventSource.onerror = error => this._zone.run(() => observer.error(error));
    });
  }

  public close(): void {
    this.getEventSource().close();
  }

  private getEventSource(): EventSource {
    return new EventSource(`${this._urlEvents}/events/${CONTEXT_WORKFLOW_ID}`);
  }
}
