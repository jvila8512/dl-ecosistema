import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './idea.reducer';

export const IdeaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ideaEntity = useAppSelector(state => state.idea.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ideaDetailsHeading">
          <Translate contentKey="jhipsterApp.idea.detail.title">Idea</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.id}</dd>
          <dt>
            <span id="numeroRegistro">
              <Translate contentKey="jhipsterApp.idea.numeroRegistro">Numero Registro</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.numeroRegistro}</dd>
          <dt>
            <span id="entidad">
              <Translate contentKey="jhipsterApp.idea.entidad">Entidad</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.entidad}</dd>
          <dt>
            <span id="titulo">
              <Translate contentKey="jhipsterApp.idea.titulo">Titulo</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.titulo}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhipsterApp.idea.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.descripcion}</dd>
          <dt>
            <span id="autor">
              <Translate contentKey="jhipsterApp.idea.autor">Autor</Translate>
            </span>
          </dt>
          <dd>{ideaEntity.autor}</dd>
          <dt>
            <span id="fechaIncripcion">
              <Translate contentKey="jhipsterApp.idea.fechaIncripcion">Fecha Incripcion</Translate>
            </span>
          </dt>
          <dd>
            {ideaEntity.fechaIncripcion ? (
              <TextFormat value={ideaEntity.fechaIncripcion} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jhipsterApp.idea.tipoIdea">Tipo Idea</Translate>
          </dt>
          <dd>{ideaEntity.tipoIdea ? ideaEntity.tipoIdea.tipoIdea : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.idea.reto">Reto</Translate>
          </dt>
          <dd>{ideaEntity.reto ? ideaEntity.reto.reto : ''}</dd>
        </dl>
        <Button tag={Link} to="/idea" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/idea/${ideaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default IdeaDetail;
