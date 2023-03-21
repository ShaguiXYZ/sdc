// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  baseUrl: 'http://localhost:3000/bff-sdc', // url bff mock server
  securityUrl: 'http://localhost:8085', // change to connect to mock server
  baseAuth: 'http://localhost:4200',
  domain: 'localhost:3000' // domain localhost
};
