import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ServicoCatalogoService } from '../../../core/services/servico-catalogo.service';

@Component({
  selector: 'app-servico-form',
  templateUrl: './servico-form.component.html',
})
export class ServicoFormComponent implements OnInit {
  form!: FormGroup;
  editando = false;
  servicoId: number | null = null;
  loading = false;
  salvando = false;
  erro = '';

  constructor(
    private fb: FormBuilder,
    private service: ServicoCatalogoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(2)]],
      descricao: [''],
      precoBase: [null, [Validators.required, Validators.min(0.01)]],
      duracaoEstimadaMinutos: [30, [Validators.required, Validators.min(1)]],
      categoria: [''],
      ativo: [true],
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editando = true;
      this.servicoId = +id;
      this.loading = true;
      this.service.buscarPorId(this.servicoId).subscribe({
        next: (servico) => {
          this.form.patchValue(servico);
          this.loading = false;
        },
        error: () => {
          this.erro = 'Serviço não encontrado.';
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
      ? this.service.atualizar(this.servicoId!, payload)
      : this.service.criar(payload);

    op.subscribe({
      next: () => {
        this.salvando = false;
        this.router.navigate(['/servicos']);
      },
      error: () => {
        this.erro = 'Erro ao salvar o serviço.';
        this.salvando = false;
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['/servicos']);
  }

  get f() {
    return this.form.controls;
  }
}

