import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CatalogoService } from '../../../core/services/catalogo.service';
import { Produto } from '../../../core/models/produto.model';

@Component({
  selector: 'app-catalogo-list',
  templateUrl: './catalogo-list.component.html',
})
export class CatalogoListComponent implements OnInit {
  produtos: Produto[] = [];
  loading = false;
  erro = '';

  constructor(private service: CatalogoService, private router: Router) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.loading = true;
    this.erro = '';
    this.service.listar().subscribe({
      next: (data) => {
        this.produtos = data;
        this.loading = false;
      },
      error: () => {
        this.erro = 'Erro ao carregar o catálogo. Verifique a conexão com o serviço.';
        this.loading = false;
      },
    });
  }

  novo(): void {
    this.router.navigate(['/catalogo/novo']);
  }

  editar(id: number): void {
    this.router.navigate(['/catalogo/editar', id]);
  }

  deletar(id: number): void {
    if (!confirm('Confirma a exclusão do produto?')) return;
    this.service.deletar(id).subscribe({
      next: () => this.carregar(),
      error: () => (this.erro = 'Erro ao excluir o produto.'),
    });
  }
}

