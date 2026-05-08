export interface ServicoCatalogo {
  id?: number;
  nome: string;
  descricao?: string;
  precoBase: number;
  duracaoEstimadaMinutos: number;
  categoria?: string;
  ativo?: boolean;
  criadoEm?: string;
  atualizadoEm?: string;
}

