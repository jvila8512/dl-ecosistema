import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';
import { Button } from 'primereact/button';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <div className="surface-0 text-700 text-center">
        <div className="text-blue-600 font-bold mb-3">
          <i className="pi pi-discord"></i>&nbsp;POWERED BY DISCORD
        </div>
        <div className="text-900 font-bold text-5xl mb-3">Join Our Design Community</div>
        <div className="text-700 text-2xl mb-5">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Velit numquam eligendi quos.</div>
        <Button label="Join Now" icon="pi pi-discord" className="font-bold px-5 py-3 p-button-raised p-button-rounded white-space-nowrap" />
      </div>
    </Row>
  );
};

export default Home;
