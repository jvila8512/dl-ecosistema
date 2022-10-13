import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Reto from './reto';
import RetoDetail from './reto-detail';
import RetoUpdate from './reto-update';
import RetoDeleteDialog from './reto-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RetoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RetoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RetoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Reto} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RetoDeleteDialog} />
  </>
);

export default Routes;
