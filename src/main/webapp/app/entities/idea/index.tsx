import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Idea from './idea';
import IdeaDetail from './idea-detail';
import IdeaUpdate from './idea-update';
import IdeaDeleteDialog from './idea-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IdeaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IdeaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IdeaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Idea} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={IdeaDeleteDialog} />
  </>
);

export default Routes;
