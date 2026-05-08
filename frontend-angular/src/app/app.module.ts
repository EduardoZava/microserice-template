import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import {
  MsalModule,
  MsalRedirectComponent,
  MsalGuard,
  MsalInterceptor,
  MSAL_INSTANCE,
  MSAL_GUARD_CONFIG,
  MSAL_INTERCEPTOR_CONFIG,
  MsalService,
  MsalBroadcastService,
} from '@azure/msal-angular';
import {
  PublicClientApplication,
  InteractionType,
  BrowserCacheLocation,
  LogLevel,
} from '@azure/msal-browser';
import { environment } from '../environments/environment';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { CatalogoListComponent } from './pages/catalogo/catalogo-list/catalogo-list.component';
import { CatalogoFormComponent } from './pages/catalogo/catalogo-form/catalogo-form.component';
import { OrdemListComponent } from './pages/ordens/ordem-list/ordem-list.component';
import { OrdemFormComponent } from './pages/ordens/ordem-form/ordem-form.component';
import { ServicoListComponent } from './pages/servicos/servico-list/servico-list.component';
import { ServicoFormComponent } from './pages/servicos/servico-form/servico-form.component';

export function MSALInstanceFactory() {
  return new PublicClientApplication({
    auth: {
      clientId: environment.msalConfig.auth.clientId,
      authority: environment.msalConfig.auth.authority,
      redirectUri: environment.msalConfig.auth.redirectUri,
    },
    cache: {
      cacheLocation: BrowserCacheLocation.LocalStorage,
    },
    system: {
      loggerOptions: {
        logLevel: LogLevel.Info,
        piiLoggingEnabled: false,
      },
    },
  });
}

export function MSALGuardConfigFactory() {
  return {
    interactionType: InteractionType.Redirect,
    authRequest: {
      scopes: environment.apiConfig.scopes,
    },
  };
}

export function MSALInterceptorConfigFactory() {
  const protectedResourceMap = new Map<string, Array<string>>();
  const toAbsoluteUrl = (url: string) =>
    url.startsWith('http') ? url : `${window.location.origin}${url}`;

  protectedResourceMap.set(toAbsoluteUrl('/api/'), environment.apiConfig.scopes);
  protectedResourceMap.set(environment.apiConfig.uri, environment.apiConfig.scopes);
  protectedResourceMap.set(toAbsoluteUrl(environment.catalogoApiUrl), environment.apiConfig.scopes);
  protectedResourceMap.set(toAbsoluteUrl(environment.servicoApiUrl), environment.apiConfig.scopes);
  protectedResourceMap.set(toAbsoluteUrl(environment.ordemApiUrl), environment.apiConfig.scopes);
  return {
    interactionType: InteractionType.Redirect,
    protectedResourceMap,
  };
}

@NgModule({
  declarations: [
    AppComponent,
    CatalogoListComponent,
    CatalogoFormComponent,
    ServicoListComponent,
    ServicoFormComponent,
    OrdemListComponent,
    OrdemFormComponent,
  ],
  imports: [
    BrowserModule,
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    MsalModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: MsalInterceptor,
      multi: true,
    },
    {
      provide: MSAL_INSTANCE,
      useFactory: MSALInstanceFactory,
    },
    {
      provide: MSAL_GUARD_CONFIG,
      useFactory: MSALGuardConfigFactory,
    },
    {
      provide: MSAL_INTERCEPTOR_CONFIG,
      useFactory: MSALInterceptorConfigFactory,
    },
    MsalService,
    MsalGuard,
    MsalBroadcastService,
  ],
  bootstrap: [AppComponent, MsalRedirectComponent],
})
export class AppModule {}
