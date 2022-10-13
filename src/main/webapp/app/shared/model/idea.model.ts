import dayjs from 'dayjs';
import { ITipoIdea } from 'app/shared/model/tipo-idea.model';
import { IReto } from 'app/shared/model/reto.model';

export interface IIdea {
  id?: number;
  numeroRegistro?: number;
  entidad?: string;
  titulo?: string;
  descripcion?: string;
  autor?: string;
  fechaIncripcion?: string | null;
  tipoIdea?: ITipoIdea | null;
  reto?: IReto | null;
}

export const defaultValue: Readonly<IIdea> = {};
