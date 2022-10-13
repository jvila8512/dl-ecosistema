import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/tipo-idea">
        <Translate contentKey="global.menu.entities.tipoIdea" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/idea">
        <Translate contentKey="global.menu.entities.idea" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/reto">
        <Translate contentKey="global.menu.entities.reto" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/ecosistema">
        <Translate contentKey="global.menu.entities.ecosistema" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/usuario-ecosistema">
        <Translate contentKey="global.menu.entities.usuarioEcosistema" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
