import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ServicoCatalogo } from '../models/servico-catalogo.model';

@Injectable({ providedIn: 'root' })
export class ServicoCatalogoService {
  private readonly baseUrl = environment.servicoApiUrl;

  constructor(private http: HttpClient) {}

  listar(): Observable<ServicoCatalogo[]> {
    return this.http.get<ServicoCatalogo[]>(this.baseUrl);
  }

  buscarPorId(id: number): Observable<ServicoCatalogo> {
    return this.http.get<ServicoCatalogo>(`${this.baseUrl}/${id}`);
  }

  criar(servico: ServicoCatalogo): Observable<ServicoCatalogo> {
    return this.http.post<ServicoCatalogo>(this.baseUrl, servico);
  }

  atualizar(id: number, servico: ServicoCatalogo): Observable<ServicoCatalogo> {
    return this.http.put<ServicoCatalogo>(`${this.baseUrl}/${id}`, servico);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}

