import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MsalGuard } from '@azure/msal-angular';
import { CatalogoListComponent } from './pages/catalogo/catalogo-list/catalogo-list.component';
import { CatalogoFormComponent } from './pages/catalogo/catalogo-form/catalogo-form.component';
import { OrdemListComponent } from './pages/ordens/ordem-list/ordem-list.component';
import { OrdemFormComponent } from './pages/ordens/ordem-form/ordem-form.component';

const routes: Routes = [
  { path: '', redirectTo: 'catalogo', pathMatch: 'full' },
  {
    path: 'catalogo',
    component: CatalogoListComponent,
    canActivate: [MsalGuard],
  },
  {
    path: 'catalogo/novo',
    component: CatalogoFormComponent,
    canActivate: [MsalGuard],
  },
  {
    path: 'catalogo/editar/:id',
    component: CatalogoFormComponent,
    canActivate: [MsalGuard],
  },
  {
    path: 'ordens',
    component: OrdemListComponent,
    canActivate: [MsalGuard],
  },
  {
    path: 'ordens/nova',
    component: OrdemFormComponent,
    canActivate: [MsalGuard],
  },
  {
    path: 'ordens/editar/:id',
    component: OrdemFormComponent,
    canActivate: [MsalGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

