export const environment = {
  production: true,
  msalConfig: {
    auth: {
      clientId: 'YOUR_ENTRA_CLIENT_ID',
      authority: 'https://login.microsoftonline.com/YOUR_TENANT_ID',
      redirectUri: 'https://your-production-domain.com',
    },
  },
  apiConfig: {
    scopes: ['api://YOUR_API_CLIENT_ID/access_as_user'],
    uri: 'https://your-production-domain.com/api/',
  },
  // Through Traefik gateway in production
  catalogoApiUrl: '/catalogo/api/produtos',
  ordemApiUrl: '/ordem/api/ordens',
};
