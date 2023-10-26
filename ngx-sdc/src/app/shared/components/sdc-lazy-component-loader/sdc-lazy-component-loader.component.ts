import { AfterViewInit, Component, ElementRef, Input, OnDestroy, Type, ViewChild, ViewContainerRef } from '@angular/core';

@Component({
  selector: 'sdc-lazy-component-loader',
  templateUrl: './sdc-lazy-component-loader.component.html',
  styleUrls: ['./sdc-lazy-component-loader.component.scss']
})
export class SdcLazyComponentLoaderComponent<C> implements OnDestroy, AfterViewInit {
  @ViewChild('componentViewRef', { read: ViewContainerRef })
  private componentRef!: ViewContainerRef;

  @Input()
  public component!: Promise<Type<C>>;
  @Input()
  public options?: { [key: string]: any };

  private observer!: IntersectionObserver;
  private ref: any;

  constructor(private readonly element: ElementRef, private readonly vcref: ViewContainerRef) {}

  ngOnDestroy(): void {
    this.observer.unobserve(this.element.nativeElement);

    console.log('component destroyed', this.component);

    if (this.ref?.destroy) {
      this.ref.destroy();
    }
  }

  ngAfterViewInit(): void {
    this.observer = new IntersectionObserver(
      entries => {
        entries.forEach(entry => {
          if (!this.ref && entry.isIntersecting) {
            console.log(this.component);

            this.createComponent();
          }
        });
      },
      { rootMargin: '0px', threshold: 0.5 }
    );

    this.observer.observe(this.element.nativeElement);
  }

  private createComponent() {
    this.vcref.clear();

    this.lazyComponent(this.options);
  }

  private lazyComponent = (options: { [key: string]: any } = {}) => {
    this.component.then(componentType => {
      this.ref = this.componentRef.createComponent(componentType);

      Object.keys(options).forEach(key => {
        (this.ref.instance as { [key: string]: any })[key] = options[key];
      });
    });
  };
}
