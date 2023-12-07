const $ = {
  get: (selector: string): HTMLElement | null => document.querySelector(selector),
  getAll: (selector: string): NodeListOf<Element> => document.querySelectorAll(selector)
};

// @howto: export deafult $ to be used in other files
export default $;
