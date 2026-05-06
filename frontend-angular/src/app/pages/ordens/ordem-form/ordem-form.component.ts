import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { OrdemService } from '../../../core/services/ordem.service';
import { StatusOrdem } from '../../../core/models/ordem-servico.model';

@Component({
  selector: 'app-ordem-form',
  templateUrl: './ordem-form.component.html',
})
export class OrdemFormComponent implements OnInit {
  form!: FormGroup;
  editando = false;
  ordemId: number | null = null;
  loading = false;
  salvando = false;
  erro = '';

  statusOptions: { value: StatusOrdem; label: string }[] = [
    { value: 'ABERTA', label: 'Aberta' },
    { value: 'EM_ANDAMENTO', label: 'Em andamento' },
    { value: 'CONCLUIDA', label: 'Concluída' },
    { value: 'CANCELADA', label: 'Cancelada' },
  ];

  constructor(
    private fb: FormBuilder,
    private service: OrdemService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      titulo: ['', [Validators.required, Validators.minLength(3)]],
      descricao: [''],
      clienteId: [null, [Validators.required, Validators.min(1)]],
      valor: [null, [Validators.required, Validators.min(0.01)]],
      status: ['ABERTA'],
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editando = true;
      this.ordemId = +id;
      this.loading = true;
      this.service.buscarPorId(this.ordemId).subscribe({
        next: (o) => {
          this.form.patchValue(o);
          this.loading = false;
        },
        error: () => {
          this.erro = 'Ordem não encontrada.';
          this.loading = false;
        },
      });
    }
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.salvando = true;
    this.erro = '';
    const payload = this.form.value;

    const op = this.editando
      ? this.service.atualizar(this.ordemId!, payload)
      : this.service.criar(payload);

    op.subscribe({
      next: () => {
        this.salvando = false;
        this.router.navigate(['/ordens']);
      },
      error: () => {
        this.erro = 'Erro ao salvar a ordem de serviço.';
        this.salvando = false;
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['/ordens']);
  }

  get f() {
    return this.form.controls;
  }
}

