import { Injectable, OnDestroy } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Subject, Subscription, filter } from 'rxjs';
import { LanguageService } from 'src/app/core/services';
import { SdcHelpBodyModel, SdcHelpConfig, SdcHelpModel } from '../models';

@Injectable({ providedIn: 'root' })
export class SdcHelpService implements OnDestroy {
  private _appendix: string = '';
  private schema?: SdcHelpModel;
  private subscriptions: Subscription[] = [];
  private data$: Subject<SdcHelpConfig> = new Subject();

  constructor(
    private readonly languageService: LanguageService,
    private readonly router: Router
  ) {
    this.subscriptions.push(
      this.languageService.asObservable().subscribe(() => {
        this.importSchema(this.languageService.getLang());
      }),
      this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(() => {
        this.router.routerState.snapshot.root.firstChild?.data?.['help'] &&
          (this.appendix = this.router.routerState.snapshot.root.firstChild?.data?.['help']);
      })
    );

    this.importSchema(this.languageService.getLang());
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  public get appendix(): string {
    return this._appendix;
  }
  public set appendix(value: string) {
    this._appendix = value;
    this.importBody(this.appendix);
  }

  public onDataChange(): Subject<SdcHelpConfig> {
    return this.data$;
  }

  public initialize(): void {
    this.appendix = this.router.routerState.snapshot.root.firstChild?.data?.['help'];
  }

  private importBody(appendix: string): void {
    if (this.schema) {
      const entries = this.schema.entries;
      const config: SdcHelpConfig = {
        labels: this.schema.labels,
        page: appendix,
        pages: Object.keys(entries).map(key => ({ key, indexEntry: entries[key].indexEntry }))
      };

      const entry = entries[appendix];

      if (entry) {
        import(`../assets/i18n/${entry.body}`).then((data: SdcHelpBodyModel) => {
          config.body = data;
          this.data$.next(config);
        });
      }
    }
  }

  private importSchema = (lang: string): void => {
    import(`../assets/i18n/${lang}.json`).then(data => {
      this.schema = data;
      this.appendix && this.importBody(this.appendix);
    });
  };
}
