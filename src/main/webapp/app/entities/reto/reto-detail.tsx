import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reto.reducer';

export const RetoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const retoEntity = useAppSelector(state => state.reto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="retoDetailsHeading">
          <Translate contentKey="jhipsterApp.reto.detail.title">Reto</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{retoEntity.id}</dd>
          <dt>
            <span id="reto">
              <Translate contentKey="jhipsterApp.reto.reto">Reto</Translate>
            </span>
          </dt>
          <dd>{retoEntity.reto}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="jhipsterApp.reto.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{retoEntity.descripcion}</dd>
          <dt>
            <Translate contentKey="jhipsterApp.reto.ecosistema">Ecosistema</Translate>
          </dt>
          <dd>{retoEntity.ecosistema ? retoEntity.ecosistema.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/reto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reto/${retoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RetoDetail;
