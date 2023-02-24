export enum TypeButton {
    PRIMARY = 'primary',
    SECONDARY = 'secondary',
    TERTIARY = 'tertiary'
}

export class ButtonModel {
    constructor(public type: TypeButton, public text: string, public action: () => void) {}
}

export class ButtonConfig {
    text?: (event?: any) => string;
    type?: (event?: any) => TypeButton;
    icon?: (event?: any) => string;
    callback?: (event?: any) => any;
    options?: any;
    super?: number | boolean;
    warn?: boolean;
    info?: string;

    constructor(text?: string, icon?: string, type?: TypeButton) {
        this.text = () => text || '';
        this.type = () => type || TypeButton.TERTIARY;
        this.icon = () => icon || '';
    }

    isVisible = (element?: any): boolean => true;
    public set visible(value: boolean) {
        this.isVisible = () => value;
    }

    isEnabled = (element?: any): boolean => true;
    public set enabled(value: boolean) {
        this.isEnabled = () => value;
    }

    public onClick(event?: any) {
        if (this.callback && this.isEnabled(event) && this.isVisible(event)) {
            return this.callback(event);
        }

        throw new Error('Method not allowed');
    }
}
