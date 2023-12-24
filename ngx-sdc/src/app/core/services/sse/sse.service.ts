import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CONTEXT_WORKFLOW_ID } from '../security';
import { SseEventDTO, SseEventModel } from './models';

@Injectable({ providedIn: 'root' })
export class SseService<T = any> {
  private _urlEvents = `${environment.baseUrl}/api`;
  private eventSource: EventSource;
  private eventObserver: Observable<SseEventModel<T>>;

  constructor() {
    this.eventSource = new EventSource(`${this._urlEvents}/events/${CONTEXT_WORKFLOW_ID}`);
    this.eventObserver = this.getServerSentEvent();
  }

  public onEvent(): Observable<SseEventModel<T>> {
    return this.eventObserver;
  }

  private getServerSentEvent(): Observable<SseEventModel<T>> {
    return new Observable<SseEventDTO<T>>(observer => {
      this.eventSource.onopen = event => {
        console.log('open sse', event);
      };

      this.eventSource.onmessage = event => {
        const sseEvent: SseEventDTO<T> = JSON.parse(event.data);
        observer.next(SseEventModel.fromDTO(sseEvent));
      };

      this.eventSource.onerror = error => {
        observer.error(error);
      };

      return () => {
        this.eventSource.close();
      };
    });
  }
}
