import { CommonModule } from '@angular/common';
import { Component, ElementRef, Input, OnDestroy, OnInit, TemplateRef, ViewChild, ViewEncapsulation } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { OverlayItemStatus } from '../../models';
import { SdcHelpConfig } from './models';
import { SdcHelpParagraphPipe } from './pipe';
import { SdcHelpService } from './services';

@Component({
  selector: 'sdc-help',
  styleUrls: ['./sdc-help.component.scss', './assets/styles/sdc-help-data.scss'],
  template: `
    @if (config) {
      <div class="sdc-help-content  {{ state }}">
        <article class="help-index sdc-scrollable">
          <header class="help-header" nxHeadline="subsection-medium" [innerHtml]="config.labels['indexTitle']"></header>
          <section class="help-index-items">
            @for (page of config.pages; track page) {
              <div
                class="help-index-item"
                [ngClass]="{ active: page.key === config.page }"
                nxHeadline="subsection-xsmall"
                [innerHtml]="page.indexEntry"
                (click)="loadHelp(page.key)"
              ></div>
            }
          </section>
        </article>
        @if (config.body) {
          <article #helpData class="help-data sdc-scrollable">
            <header class="help-header" nxHeadline="subsection-medium" [innerHtml]="config.body.title"></header>
            <section class="help-paragraphs">
              @if (config.body.media) {
                <video class="help-media" [src]="config.body.media" controls autoplay="false"></video>
              }
              @for (paragraph of config.body.paragraphs; track $index) {
                @defer (on viewport) {
                  <article class="help-paragraph">
                    @if (paragraph.title) {
                      <header class="help-paragraph-header" nxHeadline="subsection-small" [innerHtml]="paragraph.title"></header>
                    }
                    <section class="help-paragraph-body" [innerHtml]="paragraph.body | helpParagraph"></section>
                  </article>
                } @placeholder {
                  <div class="placeholder"></div>
                }
              }
            </section>
          </article>
        }
      </div>
    }
  `,
  standalone: true,
  imports: [CommonModule, NxHeadlineModule, TranslateModule, SdcHelpParagraphPipe],
  encapsulation: ViewEncapsulation.None
})
export class SdcHelpComponent implements OnInit, OnDestroy {
  @Input()
  public state: OverlayItemStatus = 'closed';

  public config?: SdcHelpConfig;
  public index: string[] = [];

  @ViewChild('helpData')
  private helpData!: ElementRef;
  private subscriptions: Subscription[] = [];

  constructor(private readonly helpService: SdcHelpService) {}

  ngOnInit(): void {
    this.subscriptions.push(
      this.helpService.onDataChange().subscribe(data => {
        this.config = data;
      })
    );

    this.helpService.initialize();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  public loadHelp(appendix: string): void {
    this.helpData.nativeElement.scrollTop = 0;
    this.helpService.appendix = appendix;
  }
}
