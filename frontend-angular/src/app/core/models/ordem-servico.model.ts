export type StatusOrdem = 'ABERTA' | 'EM_ANDAMENTO' | 'CONCLUIDA' | 'CANCELADA';

export interface OrdemServico {
  id?: number;
  titulo: string;
  descricao?: string;
  status?: StatusOrdem;
  valor: number;
  clienteId: number;
  criadoEm?: string;
  atualizadoEm?: string;
}

