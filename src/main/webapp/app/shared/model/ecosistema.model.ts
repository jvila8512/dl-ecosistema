import { IUsuarioEcosistema } from 'app/shared/model/usuario-ecosistema.model';
import { IReto } from 'app/shared/model/reto.model';

export interface IEcosistema {
  id?: number;
  nombre?: string;
  usurioecosistemas?: IUsuarioEcosistema[] | null;
  retos?: IReto[] | null;
}

export const defaultValue: Readonly<IEcosistema> = {};
