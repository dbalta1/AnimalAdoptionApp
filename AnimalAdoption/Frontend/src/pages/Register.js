import React, { useState } from 'react';
import { Form, Button, Container, Card, Alert, Navbar, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';

function Register() {
  const [form, setForm] = useState({
    ime: '',
    prezime: '',
    username: '',
    email: '',
    password: '',
    potvrdiPassword: '',
    telefon: '',
    spol: '',
    godine: '',
    adresa: '',
    uloga: 'USER',
  });

  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Validacija
    for (let key in form) {
      if (form[key] === '' && key !== 'potvrdiPassword') {
        setError('Molimo popunite sva polja.');
        setSuccess('');
        return;
      }
    }

    if (form.password !== form.potvrdiPassword) {
      setError('Lozinke se ne poklapaju.');
      setSuccess('');
      return;
    }

    // TODO: Pošalji formu na backend
    console.log('Podaci za registraciju:', form);

    setError('');
    setSuccess('Registracija uspješna!');
  };

  return (
    <>
      <Container className="mt-5" style={{ maxWidth: '600px' }}>
        <Card className="shadow">
          <Card.Body>
            <Card.Title className="text-center mb-4" style={{ color: '#FF7F50' }}>
              <h3>Registracija</h3>
            </Card.Title>

            {error && <Alert variant="danger">{error}</Alert>}
            {success && <Alert variant="success">{success}</Alert>}

            <Form onSubmit={handleSubmit}>
              <Form.Group className="mb-2">
                <Form.Label>Ime</Form.Label>
                <Form.Control name="ime" value={form.ime} onChange={handleChange} />
              </Form.Group>

              <Form.Group className="mb-2">
                <Form.Label>Prezime</Form.Label>
                <Form.Control name="prezime" value={form.prezime} onChange={handleChange} />
              </Form.Group>

              <Form.Group className="mb-2">
                <Form.Label>Korisničko ime</Form.Label>
                <Form.Control name="username" value={form.username} onChange={handleChange} />
              </Form.Group>

              <Form.Group className="mb-2">
                <Form.Label>Email</Form.Label>
                <Form.Control type="email" name="email" value={form.email} onChange={handleChange} />
              </Form.Group>

              <Form.Group className="mb-2">
                <Form.Label>Lozinka</Form.Label>
                <Form.Control type="password" name="password" value={form.password} onChange={handleChange} />
              </Form.Group>

              <Form.Group className="mb-2">
                <Form.Label>Potvrdi lozinku</Form.Label>
                <Form.Control type="password" name="potvrdiPassword" value={form.potvrdiPassword} onChange={handleChange} />
              </Form.Group>

              <Form.Group className="mb-2">
                <Form.Label>Telefon</Form.Label>
                <Form.Control name="telefon" value={form.telefon} onChange={handleChange} />
              </Form.Group>

              <Form.Group className="mb-2">
                <Form.Label>Spol</Form.Label>
                <Form.Select name="spol" value={form.spol} onChange={handleChange}>
                  <option value="">Odaberi...</option>
                  <option value="MUSKO">Muško</option>
                  <option value="ZENSKO">Žensko</option>
                </Form.Select>
              </Form.Group>

              <Form.Group className="mb-2">
                <Form.Label>Godine</Form.Label>
                <Form.Control type="number" name="godine" value={form.godine} onChange={handleChange} />
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Adresa</Form.Label>
                <Form.Control name="adresa" value={form.adresa} onChange={handleChange} />
              </Form.Group>

              <div className="d-grid">
                <Button type="submit" style={{ backgroundColor: '#FF7F50', borderColor: '#FF7F50', fontWeight: 'bold'}}>
                  Registruj se
                </Button>
              </div>

              <div className="text-center mt-3">
                Već imate nalog? <Link to="/login" style={{ color: '#FF7F50' }}>Prijavite se</Link>
              </div>
            </Form>
          </Card.Body>
        </Card>
      </Container>
    </>
  );
}

export default Register;
