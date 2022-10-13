import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITipoIdea } from 'app/shared/model/tipo-idea.model';
import { getEntities as getTipoIdeas } from 'app/entities/tipo-idea/tipo-idea.reducer';
import { IReto } from 'app/shared/model/reto.model';
import { getEntities as getRetos } from 'app/entities/reto/reto.reducer';
import { IIdea } from 'app/shared/model/idea.model';
import { getEntity, updateEntity, createEntity, reset } from './idea.reducer';

export const IdeaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const tipoIdeas = useAppSelector(state => state.tipoIdea.entities);
  const retos = useAppSelector(state => state.reto.entities);
  const ideaEntity = useAppSelector(state => state.idea.entity);
  const loading = useAppSelector(state => state.idea.loading);
  const updating = useAppSelector(state => state.idea.updating);
  const updateSuccess = useAppSelector(state => state.idea.updateSuccess);
  const handleClose = () => {
    props.history.push('/idea' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTipoIdeas({}));
    dispatch(getRetos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ideaEntity,
      ...values,
      tipoIdea: tipoIdeas.find(it => it.id.toString() === values.tipoIdea.toString()),
      reto: retos.find(it => it.id.toString() === values.reto.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...ideaEntity,
          tipoIdea: ideaEntity?.tipoIdea?.id,
          reto: ideaEntity?.reto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterApp.idea.home.createOrEditLabel" data-cy="IdeaCreateUpdateHeading">
            <Translate contentKey="jhipsterApp.idea.home.createOrEditLabel">Create or edit a Idea</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="idea-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterApp.idea.numeroRegistro')}
                id="idea-numeroRegistro"
                name="numeroRegistro"
                data-cy="numeroRegistro"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.entidad')}
                id="idea-entidad"
                name="entidad"
                data-cy="entidad"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.titulo')}
                id="idea-titulo"
                name="titulo"
                data-cy="titulo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.descripcion')}
                id="idea-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.autor')}
                id="idea-autor"
                name="autor"
                data-cy="autor"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterApp.idea.fechaIncripcion')}
                id="idea-fechaIncripcion"
                name="fechaIncripcion"
                data-cy="fechaIncripcion"
                type="date"
              />
              <ValidatedField
                id="idea-tipoIdea"
                name="tipoIdea"
                data-cy="tipoIdea"
                label={translate('jhipsterApp.idea.tipoIdea')}
                type="select"
              >
                <option value="" key="0" />
                {tipoIdeas
                  ? tipoIdeas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.tipoIdea}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="idea-reto" name="reto" data-cy="reto" label={translate('jhipsterApp.idea.reto')} type="select">
                <option value="" key="0" />
                {retos
                  ? retos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.reto}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/idea" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default IdeaUpdate;
