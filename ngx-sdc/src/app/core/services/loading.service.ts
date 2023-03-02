import { EventEmitter, Injectable, Output } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class UiLoadingService {
    @Output() public uiShowLoading: EventEmitter<boolean> = new EventEmitter();

    private numLoadings = 0;

    public get showLoading(): boolean {
        return this.numLoadings > 0;
    }
    public set showLoading(value: boolean) {
        if (value) {
            if (++this.numLoadings === 1) {
                this.uiShowLoading.emit(true);
            }
        } else if (this.numLoadings > 0) {
            if (--this.numLoadings === 0) {
                this.uiShowLoading.emit(false);
            }
        }
    }
}
