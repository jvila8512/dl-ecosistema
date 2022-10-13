import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUsuarioEcosistema } from 'app/shared/model/usuario-ecosistema.model';
import { getEntities } from './usuario-ecosistema.reducer';

export const UsuarioEcosistema = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const usuarioEcosistemaList = useAppSelector(state => state.usuarioEcosistema.entities);
  const loading = useAppSelector(state => state.usuarioEcosistema.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="usuario-ecosistema-heading" data-cy="UsuarioEcosistemaHeading">
        <Translate contentKey="jhipsterApp.usuarioEcosistema.home.title">Usuario Ecosistemas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterApp.usuarioEcosistema.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/usuario-ecosistema/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterApp.usuarioEcosistema.home.createLabel">Create new Usuario Ecosistema</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {usuarioEcosistemaList && usuarioEcosistemaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jhipsterApp.usuarioEcosistema.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.usuarioEcosistema.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.usuarioEcosistema.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterApp.usuarioEcosistema.ecosistema">Ecosistema</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {usuarioEcosistemaList.map((usuarioEcosistema, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/usuario-ecosistema/${usuarioEcosistema.id}`} color="link" size="sm">
                      {usuarioEcosistema.id}
                    </Button>
                  </td>
                  <td>{usuarioEcosistema.nombre}</td>
                  <td>{usuarioEcosistema.user ? usuarioEcosistema.user.login : ''}</td>
                  <td>
                    {usuarioEcosistema.ecosistema ? (
                      <Link to={`/ecosistema/${usuarioEcosistema.ecosistema.id}`}>{usuarioEcosistema.ecosistema.nombre}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/usuario-ecosistema/${usuarioEcosistema.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/usuario-ecosistema/${usuarioEcosistema.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/usuario-ecosistema/${usuarioEcosistema.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterApp.usuarioEcosistema.home.notFound">No Usuario Ecosistemas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default UsuarioEcosistema;
