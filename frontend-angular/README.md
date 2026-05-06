# Frontend Angular

Angular 17+ SPA with Microsoft Entra ID (MSAL) authentication preparation.

## Prerequisites
- Node.js 20+
- npm

## Setup
1. Install dependencies: `npm install --legacy-peer-deps`
2. Configure Entra ID in `src/environments/environment.ts`
3. Start: `npm start`

## Entra ID Configuration
Replace placeholders in `src/environments/environment.ts`:
- `YOUR_ENTRA_CLIENT_ID` - Application (client) ID from Azure portal
- `YOUR_TENANT_ID` - Directory (tenant) ID
- `YOUR_API_CLIENT_ID` - API application client ID

## Build
```bash
npm run build:prod
```

## Docker
```bash
docker build -t frontend-angular .
```
