// src/pages/About.js

import React from 'react';
import { Container, Row, Col, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import './About.css'; // Kreiraj ako želiš dodatne stilove

const About = () => {
  return (
    <section className="about-page py-5">
      <Container>
        <Row className="align-items-center">
          <Col md={6}>
            <img 
              src="/images/azil.jpg" 
              alt="Naš tim u azilu" 
              className="img-fluid rounded shadow"
            />
          </Col>
          <Col md={6}>
            <h2>O nama</h2>
            <p>
              Dobro došli u naš svijet pun ljubavi prema životinjama! Mi smo tim entuzijasta i volontera koji su udružili snage kako bi pomogli napuštenim životinjama da pronađu siguran dom.
            </p>
            <p>
              Naša misija je stvoriti most između skloništa i budućih udomitelja. Vjerujemo da svaka životinja zaslužuje šansu za sretniji život, a uz vašu pomoć to je moguće.
            </p>
            <p>
              Kroz ovu platformu želimo podići svijest, edukovati javnost i omogućiti transparentan i jednostavan proces udomljavanja.
            </p>
            <Button as={Link} to="/contact" className="mt-3 orange-button">
              Kontaktiraj nas
            </Button>
          </Col>
        </Row>
      </Container>
    </section>
  );
};

export default About;
