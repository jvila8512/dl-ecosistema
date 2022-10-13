import { IIdea } from 'app/shared/model/idea.model';

export interface ITipoIdea {
  id?: number;
  tipoIdea?: string | null;
  idea?: IIdea | null;
}

export const defaultValue: Readonly<ITipoIdea> = {};
