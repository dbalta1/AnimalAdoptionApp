import React, { useState } from 'react';
import { Container, Row, Col, Card, Button, Form } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const animals = [
  {
    id: 1,
    name: 'Tosi',
    breed: 'Američka akita',
    age: '2 godine',
    image: '/images/tosi.jpg',
    location: 'Jablanica'
  },
  {
    id: 2,
    name: 'Bucko',
    breed: 'Evropska mačka',
    age: '4 godine',
    image: '/images/bucko.jpg',
    location: 'Sarajevo'
  },
  {
    id: 3,
    name: 'Felix',
    breed: 'Mješanac',
    age: '1 godina',
    image: '/images/feliks2.jpg',
    location: 'Mostar'
  },
  {
    id: 4,
    name: 'Luna',
    breed: 'Zlatni retriver',
    age: '3 godine',
    image: '/images/luna.jpg',
    location: 'Sarajevo'
  }
];

const Animals = () => {
  const [selectedBreed, setSelectedBreed] = useState('');
  const [selectedLocation, setSelectedLocation] = useState('');

  const uniqueBreeds = [...new Set(animals.map(a => a.breed))];
  const uniqueLocations = [...new Set(animals.map(a => a.location))];

  const filteredAnimals = animals.filter(animal => {
    return (
      (selectedBreed === '' || animal.breed === selectedBreed) &&
      (selectedLocation === '' || animal.location === selectedLocation)
    );
  });

  return (
    <Container className="py-5">
      <h2 className="text-center mb-4">Naše životinje za udomljavanje</h2>

      {/* Filteri */}
      <Row className="mb-4">
        <Col md={6}>
          <Form.Group controlId="filterBreed">
            <Form.Label>Filtriraj po vrsti (rasi)</Form.Label>
            <Form.Select
              value={selectedBreed}
              onChange={(e) => setSelectedBreed(e.target.value)}
            >
              <option value="">Sve vrste</option>
              {uniqueBreeds.map((breed, index) => (
                <option key={index} value={breed}>{breed}</option>
              ))}
            </Form.Select>
          </Form.Group>
        </Col>
        <Col md={6}>
          <Form.Group controlId="filterLocation">
            <Form.Label>Filtriraj po lokaciji</Form.Label>
            <Form.Select
              value={selectedLocation}
              onChange={(e) => setSelectedLocation(e.target.value)}
            >
              <option value="">Sve lokacije</option>
              {uniqueLocations.map((location, index) => (
                <option key={index} value={location}>{location}</option>
              ))}
            </Form.Select>
          </Form.Group>
        </Col>
      </Row>

      {/* Lista životinja */}
      <Row>
        {filteredAnimals.map(animal => (
          <Col key={animal.id} md={4} className="mb-4">
            <Card className="animal-card h-100">
              <Card.Img variant="top" src={animal.image} />
              <Card.Body>
                <Card.Title>{animal.name}</Card.Title>
                <Card.Text>
                  <strong>Rasa:</strong> {animal.breed}<br />
                  <strong>Starost:</strong> {animal.age}<br />
                  <strong>Lokacija:</strong> {animal.location}
                </Card.Text>
              </Card.Body>
              <Card.Footer>
                <Button as={Link} to={`/animals/${animal.id}`} variant="primary" className="w-100">
                  Saznaj više
                </Button>
              </Card.Footer>
            </Card>
          </Col>
        ))}

        {/* Dodatak za novu životinju */}
        <Col md={4} className="mb-4">
          <Card className="h-100 text-center d-flex align-items-center justify-content-center p-3 border-dashed">
            <Card.Body>
              <p>Dodaj novu životinju</p>
              <Button as={Link} to="/add-animal" variant="success">
                + Dodaj
              </Button>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default Animals;
