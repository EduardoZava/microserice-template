import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrdemService } from '../../../core/services/ordem.service';
import { OrdemServico } from '../../../core/models/ordem-servico.model';

const STATUS_LABELS: Record<string, string> = {
  ABERTA: 'Aberta',
  EM_ANDAMENTO: 'Em andamento',
  CONCLUIDA: 'Concluída',
  CANCELADA: 'Cancelada',
};

@Component({
  selector: 'app-ordem-list',
  templateUrl: './ordem-list.component.html',
})
export class OrdemListComponent implements OnInit {
  ordens: OrdemServico[] = [];
  loading = false;
  erro = '';

  constructor(private service: OrdemService, private router: Router) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.loading = true;
    this.erro = '';
    this.service.listar().subscribe({
      next: (data) => {
        this.ordens = data;
        this.loading = false;
      },
      error: () => {
        this.erro = 'Erro ao carregar ordens. Verifique a conexão com o serviço.';
        this.loading = false;
      },
    });
  }

  labelStatus(status?: string): string {
    return status ? (STATUS_LABELS[status] ?? status) : '—';
  }

  nova(): void {
    this.router.navigate(['/ordens/nova']);
  }

  editar(id: number): void {
    this.router.navigate(['/ordens/editar', id]);
  }

  deletar(id: number): void {
    if (!confirm('Confirma a exclusão da ordem de serviço?')) return;
    this.service.deletar(id).subscribe({
      next: () => this.carregar(),
      error: () => (this.erro = 'Erro ao excluir a ordem.'),
    });
  }
}

