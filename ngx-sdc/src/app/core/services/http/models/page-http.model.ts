export interface PageHttp<T> {
  totalItems: number;
  totalPages: number;
  currentPage: number;
  items: T | T[];
}
