const $ = (selector: string): HTMLElement | null => document.querySelector(selector);
export const $$ = (selector: string): NodeListOf<HTMLElement> => document.querySelectorAll(selector);

// @howto: export deafult $ to be used in other files
export default $;
