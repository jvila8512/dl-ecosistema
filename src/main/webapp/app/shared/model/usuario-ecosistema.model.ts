import { IUser } from 'app/shared/model/user.model';
import { IEcosistema } from 'app/shared/model/ecosistema.model';

export interface IUsuarioEcosistema {
  id?: number;
  nombre?: string;
  user?: IUser | null;
  ecosistema?: IEcosistema | null;
}

export const defaultValue: Readonly<IUsuarioEcosistema> = {};
