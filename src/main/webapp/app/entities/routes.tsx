import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TipoIdea from './tipo-idea';
import Idea from './idea';
import Reto from './reto';
import Ecosistema from './ecosistema';
import UsuarioEcosistema from './usuario-ecosistema';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}tipo-idea`} component={TipoIdea} />
        <ErrorBoundaryRoute path={`${match.url}idea`} component={Idea} />
        <ErrorBoundaryRoute path={`${match.url}reto`} component={Reto} />
        <ErrorBoundaryRoute path={`${match.url}ecosistema`} component={Ecosistema} />
        <ErrorBoundaryRoute path={`${match.url}usuario-ecosistema`} component={UsuarioEcosistema} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
