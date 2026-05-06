import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { OrdemServico } from '../models/ordem-servico.model';

@Injectable({ providedIn: 'root' })
export class OrdemService {
  private readonly baseUrl = environment.ordemApiUrl;

  constructor(private http: HttpClient) {}

  listar(): Observable<OrdemServico[]> {
    return this.http.get<OrdemServico[]>(this.baseUrl);
  }

  buscarPorId(id: number): Observable<OrdemServico> {
    return this.http.get<OrdemServico>(`${this.baseUrl}/${id}`);
  }

  criar(ordem: OrdemServico): Observable<OrdemServico> {
    return this.http.post<OrdemServico>(this.baseUrl, ordem);
  }

  atualizar(id: number, ordem: OrdemServico): Observable<OrdemServico> {
    return this.http.put<OrdemServico>(`${this.baseUrl}/${id}`, ordem);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}

