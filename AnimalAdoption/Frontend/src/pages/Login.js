import React, { useState } from 'react';
import { Form, Button, Container, Card, Alert } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!email || !password) {
      setError('Molimo popunite sva polja');
      return;
    }

    try {
      const response = await axios.post('http://localhost:8080/auth/login', {
        email,
        password
      });

      const user = response.data;
      localStorage.setItem('userId', user.id);
      localStorage.setItem('token', user.token);

      navigate('/forum'); // redirect nakon logina
    } catch (err) {
      console.error('Greška pri loginu:', err);
      setError('Pogrešan email ili lozinka');
    }
  };

  return (
      <Container className="mt-5" style={{ maxWidth: '500px' }}>
        <Card className="shadow">
          <Card.Body>
            <Card.Title className="text-center mb-4" style={{ color: '#FF7F50' }}>
              <h3>Prijava</h3>
            </Card.Title>

            {error && <Alert variant="danger">{error}</Alert>}

            <Form onSubmit={handleSubmit}>
              <Form.Group className="mb-3">
                <Form.Label>Email adresa</Form.Label>
                <Form.Control
                    type="email"
                    placeholder="Unesite email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
              </Form.Group>

              <Form.Group className="mb-4">
                <Form.Label>Lozinka</Form.Label>
                <Form.Control
                    type="password"
                    placeholder="Unesite lozinku"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
              </Form.Group>

              <div className="d-grid mb-3">
                <Button
                    variant="primary"
                    type="submit"
                    style={{
                      backgroundColor: '#FF7F50',
                      borderColor: '#FF7F50',
                      fontWeight: 'bold'
                    }}
                >
                  Prijavi se
                </Button>
              </div>

              <div className="text-center">
                <p>
                  Nemate nalog?{' '}
                  <Link to="/register" style={{ color: '#FF7F50' }}>
                    Registrujte se
                  </Link>
                </p>
              </div>
            </Form>
          </Card.Body>
        </Card>
      </Container>
  );
}

export default Login;
