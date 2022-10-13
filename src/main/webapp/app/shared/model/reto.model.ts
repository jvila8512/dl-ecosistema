import { IIdea } from 'app/shared/model/idea.model';
import { IEcosistema } from 'app/shared/model/ecosistema.model';

export interface IReto {
  id?: number;
  reto?: string;
  descripcion?: string;
  ideas?: IIdea[] | null;
  ecosistema?: IEcosistema | null;
}

export const defaultValue: Readonly<IReto> = {};
