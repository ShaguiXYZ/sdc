export class UriServiceMock {
  componentUriByType() {
    return Promise.resolve({ url: 'https://www.example.com' });
  }
}
