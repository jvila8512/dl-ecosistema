import tipoIdea from 'app/entities/tipo-idea/tipo-idea.reducer';
import idea from 'app/entities/idea/idea.reducer';
import reto from 'app/entities/reto/reto.reducer';
import ecosistema from 'app/entities/ecosistema/ecosistema.reducer';
import usuarioEcosistema from 'app/entities/usuario-ecosistema/usuario-ecosistema.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  tipoIdea,
  idea,
  reto,
  ecosistema,
  usuarioEcosistema,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
