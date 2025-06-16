import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Container, Card, Button, Row, Col, Form, Alert } from 'react-bootstrap';
import { FaHeart, FaRegHeart, FaPaw } from 'react-icons/fa';

const AnimalProfile = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [animal, setAnimal] = useState(null);
  const [isFavorite, setIsFavorite] = useState(false);
  const [showAdoptionForm, setShowAdoptionForm] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phone: '',
    address: '',
    experience: '',
    message: ''
  });
  const [submitted, setSubmitted] = useState(false);

  // Simulacija dobavljanja podataka o životinji
  useEffect(() => {
    // Ovo bi u praksi bilo API poziv
    const mockAnimals = [
      {
        id: 1,
        name: 'Tosi',
        breed: 'Američka akita',
        age: 2,
        gender: 'Muško',
        size: 'Veliki',
        energy: 'Visoka',
        goodWithKids: false,
        goodWithPets: true,
        healthIssues: false,
        location: 'Ostrožac',
        description: 'Veseo i energičan pas koji voli šetnje i igru.',
        image: '/images/tosi.jpg'
      },
      {
        id: 2,
        name: 'Bucko',
        breed: 'Evropska mačka',
        age: 4,
        gender: 'Muško',
        size: 'Mali',
        energy: 'Srednja',
        goodWithKids: true,
        goodWithPets: false,
        healthIssues: false,
        location: 'Sarajevo',
        description: 'Mirna i nježna mačka koja voli maženje i dobru hranu.',
        image: '/images/bucko.jpg'
      }
    ];

    const foundAnimal = mockAnimals.find(a => a.id === parseInt(id));
    setAnimal(foundAnimal);

    // Provjera da li je u omiljenim (simulacija)
    const favorites = JSON.parse(localStorage.getItem('favorites')) || [];
    setIsFavorite(favorites.includes(parseInt(id)));
  }, [id]);

  const toggleFavorite = () => {
    const favorites = JSON.parse(localStorage.getItem('favorites')) || [];
    if (isFavorite) {
      const updatedFavorites = favorites.filter(favId => favId !== parseInt(id));
      localStorage.setItem('favorites', JSON.stringify(updatedFavorites));
    } else {
      localStorage.setItem('favorites', JSON.stringify([...favorites, parseInt(id)]));
    }
    setIsFavorite(!isFavorite);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Ovdje bi bio API poziv za slanje zahtjeva
    console.log('Podaci za udomljavanje:', formData);
    setSubmitted(true);
    setShowAdoptionForm(false);
  };

  if (!animal) {
    return <Container className="text-center mt-5"><h2>Životinja nije pronađena</h2></Container>;
  }

  return (
    <Container className="my-5">
      <Button variant="outline-secondary" onClick={() => navigate(-1)} className="mb-4">
        &larr; Nazad na listu
      </Button>

      <Card className="shadow">
        <Row>
          <Col md={6}>
            <Card.Img 
              variant="top" 
              src={animal.image} 
              alt={animal.name}
              className="animal-image"
            />
          </Col>
          <Col md={6}>
            <Card.Body>
              <div className="d-flex justify-content-between align-items-start">
                <Card.Title className="display-4">{animal.name}</Card.Title>
                <Button 
                  variant="link" 
                  onClick={toggleFavorite}
                  aria-label={isFavorite ? "Ukloni iz omiljenih" : "Dodaj u omiljene"}
                >
                  {isFavorite ? <FaHeart color="red" size={28} /> : <FaRegHeart size={28} />}
                </Button>
              </div>

              <Card.Text className="text-muted mb-4">
                <FaPaw className="me-2" />
                {animal.breed} • {animal.age} godine • {animal.gender}
              </Card.Text>

              <div className="mb-4">
                <h5>Osnovne informacije:</h5>
                <ul>
                  <li>Veličina: {animal.size}</li>
                  <li>Energija: {animal.energy}</li>
                  <li>Slaganje sa djecom: {animal.goodWithKids ? 'Da' : 'Ne'}</li>
                  <li>Slaganje drugim životinjama: {animal.goodWithPets ? 'Da' : 'Ne'}</li>
                  <li>Zdravstveni problemi: {animal.healthIssues ? 'Da' : 'Ne'}</li>
                  <li>Lokacija: {animal.location}</li>
                </ul>
              </div>

              <Card.Text className="mb-4">
                <h5>Opis:</h5>
                {animal.description}
              </Card.Text>

              <div className="d-flex gap-3">
                <Button 
                  variant="primary" 
                  size="lg"
                  onClick={() => setShowAdoptionForm(true)}
                >
                  Udomi me
                </Button>
              </div>
            </Card.Body>
          </Col>
        </Row>
      </Card>

      {/* Forma za udomljavanje */}
      {showAdoptionForm && (
        <Card className="mt-4 shadow">
          <Card.Body>
            <Card.Title className="mb-4">Forma za udomljavanje</Card.Title>
            
            {submitted ? (
              <Alert variant="success">
                Hvala na interesovanju! Kontaktiraćemo vas uskoro.
              </Alert>
            ) : (
              <Form onSubmit={handleSubmit}>
                <Row className="mb-3">
                  <Col md={6}>
                    <Form.Group controlId="formName">
                      <Form.Label>Ime i prezime *</Form.Label>
                      <Form.Control
                        type="text"
                        name="name"
                        value={formData.name}
                        onChange={handleInputChange}
                        required
                      />
                    </Form.Group>
                  </Col>
                  <Col md={6}>
                    <Form.Group controlId="formEmail">
                      <Form.Label>Email *</Form.Label>
                      <Form.Control
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleInputChange}
                        required
                      />
                    </Form.Group>
                  </Col>
                </Row>

                <Row className="mb-3">
                  <Col md={6}>
                    <Form.Group controlId="formPhone">
                      <Form.Label>Telefon *</Form.Label>
                      <Form.Control
                        type="tel"
                        name="phone"
                        value={formData.phone}
                        onChange={handleInputChange}
                        required
                      />
                    </Form.Group>
                  </Col>
                  <Col md={6}>
                    <Form.Group controlId="formAddress">
                      <Form.Label>Adresa *</Form.Label>
                      <Form.Control
                        type="text"
                        name="address"
                        value={formData.address}
                        onChange={handleInputChange}
                        required
                      />
                    </Form.Group>
                  </Col>
                </Row>

                <Form.Group className="mb-3" controlId="formExperience">
                  <Form.Label>Iskustvo sa kućnim ljubimcima</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={3}
                    name="experience"
                    value={formData.experience}
                    onChange={handleInputChange}
                  />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formMessage">
                  <Form.Label>Poruka (opciono)</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={3}
                    name="message"
                    value={formData.message}
                    onChange={handleInputChange}
                  />
                </Form.Group>

                <div className="d-flex justify-content-end gap-2">
                  <Button 
                    variant="outline-secondary" 
                    onClick={() => setShowAdoptionForm(false)}
                  >
                    Odustani
                  </Button>
                  <Button variant="primary" type="submit">
                    Pošalji zahtjev
                  </Button>
                </div>
              </Form>
            )}
          </Card.Body>
        </Card>
      )}
    </Container>
  );
};

export default AnimalProfile;