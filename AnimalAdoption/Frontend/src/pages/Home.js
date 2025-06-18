import React from 'react';
import { Container, Row, Col, Card, Button, Carousel } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import './Home.css'; // Dodajemo poseban CSS za ovu stranicu
import ShelterMap from '../components/ShelterMap'; // ili './ShelterMap' ako se nalazi u istom folderu


const featuredAnimals = [
  {
    id: 1,
    name: 'Tosi',
    breed: 'Američka akita',
    age: '2 godine',
    image: '/images/tosi.jpg'
  },
  {
    id: 2,
    name: 'Bucko',
    breed: 'Evropska mačka',
    age: '4 godine',
    image: '/images/bucko.jpg'
  },
  {
    id: 3,
    name: 'Felix',
    breed: 'Mješanac',
    age: '1 godina',
    image: '/images/feliks2.jpg'
  }
];

const Home = () => {
  return (
    <div className="home-page">
      {/* Hero sekcija */}
      <section className="hero-section">
        <Carousel fade>
          <Carousel.Item>
           
            <Carousel.Caption>
              <h1>Pronađite svog novog najboljeg prijatelja</h1>
              <p>Pomozite napuštenim životinjama da pronađu svoj dom</p>
              <Button 
                as={Link} 
                to="/register" 
                className="cta-button"
              >
                Postani udomitelj
              </Button>
            </Carousel.Caption>
          </Carousel.Item>
        </Carousel>
      </section>

      {/* Featured animals */}
      <section className="featured-animals py-5">
        <Container>
          <h2 className="text-center mb-5">Udomite nekog od naših štićenika</h2>
          <Row>
            {featuredAnimals.map(animal => (
              <Col key={animal.id} md={4} className="mb-4">
                <Card className="animal-card h-100">
                  <Card.Img variant="top" src={animal.image} />
                  <Card.Body>
                    <Card.Title>{animal.name}</Card.Title>
                    <Card.Text>
                      <strong>Rasa:</strong> {animal.breed}<br />
                      <strong>Starost:</strong> {animal.age}
                    </Card.Text>
                  </Card.Body>
                  <Card.Footer>
                    <Button 
                      as={Link} 
                      to={`/animals/${animal.id}`} 
                      variant="primary"
                      className="w-100 adopt-button"
                    >
                      Saznaj više
                    </Button>
                  </Card.Footer>
                </Card>
              </Col>
            ))}
          </Row>
        </Container>
      </section>

      {/* How to help section */}
     
      <section className="help-section py-5 bg-light">
        <Container>
          <h2 className="text-center mb-5">Kako možete pomoći napuštenim životinjama?</h2>
          <Row>
            <Col md={3} className="text-center mb-4">
              <div className="help-icon mb-3">
                <i className="bi bi-cash-coin" style={{ fontSize: '2rem', color: '#FF7F50' }}></i>
              </div>
              <h3>
                <Link to="/donate" style={{ textDecoration: 'none', color: 'inherit' }}>
                  Donirajte
                </Link>
              </h3>
              <p>Vaša finansijska pomoć nam omogućava bolju njegu</p>
            </Col>

            <Col md={3} className="text-center mb-4">
              <div className="help-icon mb-3">
                <i className="bi bi-people" style={{ fontSize: '2rem', color: '#FF7F50' }}></i>
              </div>
              <h3>
                <Link to="/volunteer" style={{ textDecoration: 'none', color: 'inherit' }}>
                  Volontirajte
                </Link>
              </h3>
              <p>Posvetite svoje vrijeme i ljubav našim štićenicima</p>
            </Col>

            <Col md={3} className="text-center mb-4">
              <div className="help-icon mb-3">
                <i className="bi bi-house-heart" style={{ fontSize: '2rem', color: '#FF7F50' }}></i>
              </div>
              <h3>
                <Link to="/forum" style={{ textDecoration: 'none', color: 'inherit' }}>
                  Educirajte se
                </Link>
              </h3>
              <p>Odgovorna briga o životinjama počinje edukacijom i razumijevanjem njihovih potreba</p>
            </Col>

            <Col md={3} className="text-center mb-4">
              <div className="help-icon mb-3">
                <i className="bi bi-chat-left-text" style={{ fontSize: '2rem', color: '#FF7F50' }}></i>
              </div>
              <h3>
                <Link to="/forum" style={{ textDecoration: 'none', color: 'inherit' }}>
                  Razmijenite iskustva
                </Link>
              </h3>
              <p>Razmjenom znanja, iskustava i savjeta doprinosite boljoj zaštiti i njezi životinja</p>
            </Col>
          </Row>
        </Container>
      </section>



      {/* Call to action */}
      <section className="cta-section py-5 text-white">
        <Container className="text-center">
          <h2 className="mb-4">Da li ste spremni da uljepšate život napuštenoj životinji?</h2>
          <h3 className="mb-4">Pronađite najbliže sklonište</h3>

          <div className="map-wrapper mt-4">
            <ShelterMap />
          </div>
          
        </Container>
      </section>

    </div>
  );
};

export default Home;