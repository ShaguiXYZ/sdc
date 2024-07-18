import { AfterViewInit, Directive, EventEmitter, OnDestroy, Output, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[nxInView]',
  exportAs: 'intersection',
  standalone: true
})

/**
 * IS VISIBLE DIRECTIVE
 * --------------------
 * Mounts a component whenever it is visible to the user
 * Usage: <div *isVisible>I'm on screen!</div>
 */
export class InViewDirective implements AfterViewInit, OnDestroy {
  @Output()
  public isVisible: EventEmitter<void> = new EventEmitter();

  private observer?: IntersectionObserver;

  constructor(private vcRef: ViewContainerRef) {}

  ngAfterViewInit() {
    const observedElement = this.vcRef.element.nativeElement.parentElement;

    this.observer = new IntersectionObserver(([entry]) => {
      this.isInView(entry.isIntersecting);
    });

    this.observer.observe(observedElement);
  }

  ngOnDestroy(): void {
    this.observer?.disconnect();
  }

  private isInView(isIntersecting: boolean) {
    this.vcRef.clear();

    if (isIntersecting) {
      this.isVisible.emit();
    }
  }
}
