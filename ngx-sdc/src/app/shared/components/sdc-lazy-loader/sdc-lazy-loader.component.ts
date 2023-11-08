import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnDestroy, Output } from '@angular/core';
import { UniqueIds } from 'src/app/core/lib';
import { GenericDataInfo } from 'src/app/core/models';

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
  public size: GenericDataInfo<number> = {};

  private observer!: IntersectionObserver;
  private elementObserved!: Element;

  constructor(private readonly element: ElementRef) {}

  @Input()
  public set reservedSize(value: { height?: number; width?: number }) {
    delete this.size['height.px'];
    if (value.height) {
      this.size['height.px'] = value.height;
    }

    delete this.size['width.px'];
    if (value.width) {
      this.size['width.px'] = value.width;
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
        this.size = {};
        this.inView.emit(entry.target);
        observer.unobserve(this.elementObserved);
      }
    });
  };
}
