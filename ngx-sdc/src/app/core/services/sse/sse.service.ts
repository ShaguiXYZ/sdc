import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CONTEXT_WORKFLOW_ID } from '../security';
import { SseEventModel } from './models';

@Injectable({ providedIn: 'root' })
export class SseService {
  private _urlEvents = `${environment.baseUrl}/api`;
  private eventSource: EventSource;
  private eventObserver: Observable<SseEventModel>;

  constructor() {
    this.eventSource = new EventSource(`${this._urlEvents}/events/${CONTEXT_WORKFLOW_ID}`);
    this.eventObserver = this.getServerSentEvent();
  }

  private getServerSentEvent(): Observable<SseEventModel> {
    return new Observable<SseEventModel>(observer => {
      this.eventSource.onopen = event => {
        console.log('open sse', event);
      };

      this.eventSource.onmessage = event => {
        const sseEvent: SseEventModel = JSON.parse(event.data);
        observer.next(sseEvent);
      };

      this.eventSource.onerror = error => {
        observer.error(error);
      };

      return () => {
        this.eventSource.close();
      };
    });
  }

  public getServerSentEventObserver(): Observable<SseEventModel> {
    return this.eventObserver;
  }
}
