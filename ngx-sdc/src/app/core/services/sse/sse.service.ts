import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CONTEXT_WORKFLOW_ID } from '../security';
import { SseEventModel } from './models';

/**
 * (SSE - is a technology where a browser receives automatic updates from a
 * server via HTTP connection)
 *
 * @howto server-sent events client
 */
@Injectable({ providedIn: 'root' })
export class SseService {
  private readonly _urlEvents = `${environment.baseUrl}/api`;
  private _eventSource?: EventSource;

  constructor(private readonly _zone: NgZone) {}

  onEvent(): Observable<SseEventModel> {
    return new Observable<SseEventModel>(observer => {
      this._eventSource = this.createEventSource();

      this._eventSource.onmessage = event => this._zone.run(() => observer.next(SseEventModel.fromDTO(JSON.parse(event.data))));
      this._eventSource.onerror = error =>
        this._zone.run(() => {
          observer.error(error);
          this.close(); // Ensure the connection is closed on error
        });
    });
  }

  public close(): void {
    if (this._eventSource) {
      this._eventSource.close();
      this._eventSource = undefined;
    }
  }

  private createEventSource(): EventSource {
    console.log(`SSE: ${this._urlEvents}/events/${CONTEXT_WORKFLOW_ID}`);

    return new EventSource(`${this._urlEvents}/events/${CONTEXT_WORKFLOW_ID}`);
  }
}
