/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */

export enum Languages {
  enGB = 'en-GB',
  esCO = 'es-CO',
  esES = 'es-ES',
  esBR = 'pt-BR',
  esPT = 'pt-PT'
}

type Language = keyof typeof Languages;
const isValid = (value: string): value is Language => value in Languages;

export namespace Languages {
  export const keys = (): string[] => {
    const optionsLanguages = Object.keys(Languages);
    optionsLanguages.splice(optionsLanguages.length - 2, 2); // Removes keys and valueOf functions

    return optionsLanguages;
  };

  export const valueOf = (key: string) => {
    if (isValid(key)) {
      return Languages[key];
    }

    return undefined;
  };
}
