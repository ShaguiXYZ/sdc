import { AfterViewInit, Component, ElementRef, Input, OnDestroy, Output, EventEmitter } from '@angular/core';
import { GenericDataInfo } from 'src/app/core/interfaces/dataInfo';
import { UniqueIds } from 'src/app/core/lib';

@Component({
  selector: 'sdc-lazy-loader',
  templateUrl: './sdc-lazy-loader.component.html',
  styleUrls: ['./sdc-lazy-loader.component.scss']
})
export class SdcLazyLoaderComponent implements OnDestroy, AfterViewInit {
  @Output()
  public inView: EventEmitter<Element> = new EventEmitter();

  public readonly id = `_${UniqueIds.next()}_`;
  public loaded = false;
  public styleSize: GenericDataInfo<number> = {};

  private observer!: IntersectionObserver;
  private elementObserved!: Element;

  constructor(private readonly element: ElementRef) {}

  @Input()
  public set size(value: { height?: number; width?: number }) {
    delete this.styleSize['height.px'];
    if (value.height) {
      this.styleSize['height.px'] = value.height;
    }

    delete this.styleSize['width.px'];
    if (value.width) {
      this.styleSize['width.px'] = value.width;
    }
  }

  ngOnDestroy(): void {
    this.observer.unobserve(this.elementObserved);
    this.observer.disconnect();
  }

  ngAfterViewInit(): void {
    this.observer = new IntersectionObserver(this.callback, {
      root: null,
      rootMargin: '0px',
      threshold: 0
    });

    this.elementObserved = this.element.nativeElement.querySelector(`#${this.id}`) as Element;
    this.observer.observe(this.elementObserved);
  }

  private callback = (entries: IntersectionObserverEntry[], observer: IntersectionObserver): void => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        this.loaded = true;
        this.styleSize = {};
        this.inView.emit(entry.target);
        observer.unobserve(this.elementObserved);
      }
    });
  };
}
