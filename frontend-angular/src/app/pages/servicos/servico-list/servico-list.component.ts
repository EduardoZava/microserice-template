import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServicoCatalogoService } from '../../../core/services/servico-catalogo.service';
import { ServicoCatalogo } from '../../../core/models/servico-catalogo.model';

@Component({
  selector: 'app-servico-list',
  templateUrl: './servico-list.component.html',
})
export class ServicoListComponent implements OnInit {
  servicos: ServicoCatalogo[] = [];
  loading = false;
  erro = '';

  constructor(private service: ServicoCatalogoService, private router: Router) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.loading = true;
    this.erro = '';
    this.service.listar().subscribe({
      next: (data) => {
        this.servicos = data;
        this.loading = false;
      },
      error: () => {
        this.erro = 'Erro ao carregar os serviços do catálogo.';
        this.loading = false;
      },
    });
  }

  novo(): void {
    this.router.navigate(['/servicos/novo']);
  }

  editar(id: number): void {
    this.router.navigate(['/servicos/editar', id]);
  }

  deletar(id: number): void {
    if (!confirm('Confirma a exclusão do serviço?')) return;
    this.service.deletar(id).subscribe({
      next: () => this.carregar(),
      error: () => (this.erro = 'Erro ao excluir o serviço.'),
    });
  }
}

