import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Container, Card, Button, Row, Col, Form, Alert } from 'react-bootstrap';
import { FaHeart, FaRegHeart, FaPaw } from 'react-icons/fa';
import './AnimalProfile.css';

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
    livingType: '',
    experience: '',
    reason: '',
    additional: ''
  });
  const [submitted, setSubmitted] = useState(false);

  // Mock data for animals
  useEffect(() => {
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
        location: 'Jablanica',
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
        healthIssues: true,
        location: 'Sarajevo',
        description: 'Mirna i nježna mačka koja voli maženje i dobru hranu.',
        image: '/images/bucko.jpg'
      }
    ];

    const foundAnimal = mockAnimals.find(a => a.id === parseInt(id));
    setAnimal(foundAnimal);

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
    console.log('Adoption request:', {
      animalId: animal.id,
      animalName: animal.name,
      ...formData
    });
    setSubmitted(true);
    setTimeout(() => {
      setShowAdoptionForm(false);
      setSubmitted(false);
      setFormData({
        name: '',
        email: '',
        phone: '',
        address: '',
        livingType: '',
        experience: '',
        reason: '',
        additional: ''
      });
    }, 5000);
  };

  if (!animal) {
    return <Container className="text-center mt-5"><h2>Životinja nije pronađena</h2></Container>;
  }

  return (
    <Container className="my-5">
      <Button 
        variant="outline-secondary" 
        onClick={() => navigate(-1)} 
        className="mb-4 back-button"
      >
        &larr; Nazad na listu
      </Button>

      <Card className="shadow animal-card">
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
                <Card.Title className="animal-name">{animal.name}</Card.Title>
                <Button 
                  variant="link" 
                  onClick={toggleFavorite}
                  aria-label={isFavorite ? "Ukloni iz omiljenih" : "Dodaj u omiljene"}
                  className="favorite-button"
                >
                  {isFavorite ? <FaHeart color="#FF7F50" size={28} /> : <FaRegHeart size={28} />}
                </Button>
              </div>

              <Card.Text className="animal-meta">
                <FaPaw className="me-2 paw-icon" />
                {animal.breed} • {animal.age} godine • {animal.gender}
              </Card.Text>

              <div className="animal-info mb-4">
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

              <Card.Text className="animal-description mb-4">
                <h5>Opis:</h5>
                {animal.description}
              </Card.Text>

              <div className="d-flex gap-3">
                <Button 
                  className="adopt-button"
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

      {/* Adoption Form */}
      {showAdoptionForm && (
        <Card className="mt-4 shadow adoption-form-card">
          <Card.Body>
            <Card.Title className="mb-4 form-title">
              <h3 style={{ color: '#FF7F50' }}>Forma za udomljavanje</h3>
              <p className="text-muted">Podaci za {animal.name}</p>
            </Card.Title>
            
            {submitted ? (
              <Alert variant="success" className="mt-3 success-alert">
                <Alert.Heading>Hvala na interesovanju!</Alert.Heading>
                <p>
                  Vaš zahtjev za udomljavanje je primljen. Kontaktirat ćemo vas u najkraćem mogućem roku.
                </p>
                <hr />
                <p className="mb-0">
                  Ukoliko imate hitnih pitanja, možete nas kontaktirati na telefon 033/123-456.
                </p>
              </Alert>
            ) : (
              <Form onSubmit={handleSubmit}>
                <Row className="mb-3">
                  <Col md={6}>
                    <Form.Group controlId="formName" className="mb-3">
                      <Form.Label>Ime i prezime *</Form.Label>
                      <Form.Control
                        type="text"
                        name="name"
                        value={formData.name}
                        onChange={handleInputChange}
                        required
                        placeholder="Unesite vaše puno ime"
                      />
                    </Form.Group>
                  </Col>
                  <Col md={6}>
                    <Form.Group controlId="formEmail" className="mb-3">
                      <Form.Label>Email adresa *</Form.Label>
                      <Form.Control
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleInputChange}
                        required
                        placeholder="primjer@email.com"
                      />
                    </Form.Group>
                  </Col>
                </Row>

                <Row className="mb-3">
                  <Col md={6}>
                    <Form.Group controlId="formPhone" className="mb-3">
                      <Form.Label>Broj telefona *</Form.Label>
                      <Form.Control
                        type="tel"
                        name="phone"
                        value={formData.phone}
                        onChange={handleInputChange}
                        required
                        placeholder="+387 61 123 456"
                      />
                    </Form.Group>
                  </Col>
                  <Col md={6}>
                    <Form.Group controlId="formAddress" className="mb-3">
                      <Form.Label>Mjesto stanovanja *</Form.Label>
                      <Form.Control
                        type="text"
                        name="address"
                        value={formData.address}
                        onChange={handleInputChange}
                        required
                        placeholder="Unesite grad/opštinu"
                      />
                    </Form.Group>
                  </Col>
                </Row>

                <Form.Group className="mb-3" controlId="formLiving">
                  <Form.Label>Vrsta stambenog prostora *</Form.Label>
                  <Form.Select 
                    name="livingType"
                    value={formData.livingType}
                    onChange={handleInputChange}
                    required
                  >
                    <option value="">Izaberite...</option>
                    <option value="house">Kuća sa dvorištem</option>
                    <option value="apartment">Stan</option>
                    <option value="other">Drugo</option>
                  </Form.Select>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formExperience">
                  <Form.Label>Vaše iskustvo sa kućnim ljubimcima *</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={3}
                    name="experience"
                    value={formData.experience}
                    onChange={handleInputChange}
                    required
                    placeholder="Imate li iskustva sa životinjama? Da li trenutno imate kućne ljubimce?"
                  />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formReason">
                  <Form.Label>Zašto želite udomiti ovu životinju? *</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={3}
                    name="reason"
                    value={formData.reason}
                    onChange={handleInputChange}
                    required
                    placeholder="Opišite zašto ste prava osoba za udomljavanje..."
                  />
                </Form.Group>

                <Form.Group className="mb-4" controlId="formAdditional">
                  <Form.Label>Dodatne napomene (opciono)</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={2}
                    name="additional"
                    value={formData.additional}
                    onChange={handleInputChange}
                    placeholder="Ostale informacije koje smatrate važnim..."
                  />
                </Form.Group>

                <div className="d-flex justify-content-between align-items-center">
                  <Form.Group controlId="formAgreement" className="mb-3">
                    <Form.Check
                      type="checkbox"
                      label="Slažem se sa uslovima udomljavanja"
                      required
                    />
                  </Form.Group>

                  <div className="d-flex gap-2 form-buttons">
                    <Button 
                      variant="outline-secondary" 
                      onClick={() => setShowAdoptionForm(false)}
                      className="cancel-button"
                    >
                      Odustani
                    </Button>
                    <Button 
                      type="submit"
                      className="submit-button"
                    >
                      Pošalji zahtjev
                    </Button>
                  </div>
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