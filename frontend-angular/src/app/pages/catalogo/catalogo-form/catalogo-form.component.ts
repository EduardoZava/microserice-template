import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CatalogoService } from '../../../core/services/catalogo.service';

@Component({
  selector: 'app-catalogo-form',
  templateUrl: './catalogo-form.component.html',
})
export class CatalogoFormComponent implements OnInit {
  form!: FormGroup;
  editando = false;
  produtoId: number | null = null;
  loading = false;
  salvando = false;
  erro = '';

  constructor(
    private fb: FormBuilder,
    private service: CatalogoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(2)]],
      descricao: [''],
      preco: [null, [Validators.required, Validators.min(0.01)]],
      estoque: [0, [Validators.required, Validators.min(0)]],
      categoria: [''],
      ativo: [true],
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editando = true;
      this.produtoId = +id;
      this.loading = true;
      this.service.buscarPorId(this.produtoId).subscribe({
        next: (p) => {
          this.form.patchValue(p);
          this.loading = false;
        },
        error: () => {
          this.erro = 'Produto não encontrado.';
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
      ? this.service.atualizar(this.produtoId!, payload)
      : this.service.criar(payload);

    op.subscribe({
      next: () => {
        this.salvando = false;
        this.router.navigate(['/catalogo']);
      },
      error: () => {
        this.erro = 'Erro ao salvar o produto.';
        this.salvando = false;
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['/catalogo']);
  }

  get f() {
    return this.form.controls;
  }
}

