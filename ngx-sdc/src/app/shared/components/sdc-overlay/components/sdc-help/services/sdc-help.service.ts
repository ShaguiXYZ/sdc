import { Injectable, OnDestroy } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { DataInfo } from '@shagui/ng-shagui/core';
import { Subject, Subscription, filter } from 'rxjs';
import { LanguageService } from 'src/app/core/services';
import { SdcHelpEntry } from '../constants';
import { SdcHelpConfig, SdcHelpEntryModel, SdcHelpModel } from '../models';

@Injectable({ providedIn: 'root' })
export class SdcHelpService implements OnDestroy {
  private _appendix = SdcHelpEntry.INTRODUCTORY;
  private subscriptions: Subscription[] = [];
  private data$: Subject<SdcHelpConfig> = new Subject();

  constructor(
    private readonly languageService: LanguageService,
    private readonly router: Router
  ) {
    this.subscriptions.push(
      this.languageService.asObservable().subscribe(() => {
        this.importHelpConfig(this.appendix, this.languageService.getLang());
      }),
      this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(this.initAppendix)
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  public get appendix(): keyof DataInfo<SdcHelpEntryModel, SdcHelpEntry> {
    return this._appendix;
  }
  public set appendix(value: keyof DataInfo<SdcHelpEntryModel, SdcHelpEntry>) {
    this._appendix = value;
    this.importHelpConfig(this.appendix);
  }

  public onDataChange(): Subject<SdcHelpConfig> {
    return this.data$;
  }

  public initialize = (): void => {
    this.initAppendix();
    this.importHelpConfig(this.appendix);
  };

  private initAppendix = (): void => {
    this.appendix = this.router.routerState.snapshot.root.firstChild?.data?.['help'] ?? SdcHelpEntry.INTRODUCTORY;
  };

  private importHelpConfig = async (
    appendix: SdcHelpEntry = SdcHelpEntry.INTRODUCTORY,
    lang: string = this.languageService.getLang()
  ): Promise<void> => import(`../assets/i18n/${lang}.json`).then(schema => schema && this.importBody(appendix, schema));

  private importBody(appendix: SdcHelpEntry, schema: SdcHelpModel): void {
    const entries = schema.entries;
    const config: SdcHelpConfig = {
      labels: schema.labels,
      page: appendix,
      pages: Object.keys(entries).map(key => ({
        key: key as SdcHelpEntry,
        indexEntry: entries[key as SdcHelpEntry].indexEntry
      }))
    };

    const entry = entries[appendix];

    if (entry) {
      import(`../assets/i18n/${entry.body}`).then(body => {
        config.body = body;
        this.data$.next(config);
      });
    }
  }
}
