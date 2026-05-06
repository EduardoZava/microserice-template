export const environment = {
  production: false,
  msalConfig: {
    auth: {
      clientId: 'YOUR_ENTRA_CLIENT_ID',
      authority: 'https://login.microsoftonline.com/YOUR_TENANT_ID',
      redirectUri: 'http://localhost:4200',
    },
  },
  apiConfig: {
    scopes: ['api://YOUR_API_CLIENT_ID/access_as_user'],
    uri: 'http://localhost:8080/api/',
  },
  // Direct service URLs for local dev (ng serve without Traefik)
  catalogoApiUrl: 'http://localhost:8083/api/produtos',
  ordemApiUrl: 'http://localhost:8081/api/ordens',
};
