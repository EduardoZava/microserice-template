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
};
