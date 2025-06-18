import React, { useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './DonationForm.css';

const DonationForm = ({ userId }) => {
  const [formData, setFormData] = useState({
    iznos: '',
    vrstaDonacije: 'novčana',
    opisDonacije: '',
    korisnikId: userId || 1,
    datumDonacije: new Date().toISOString().split('T')[0],
  });

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    const iznosNum = parseFloat(formData.iznos);
    if (isNaN(iznosNum)) {
      setError('Molimo unesite validan iznos');
      return;
    }

    if (iznosNum <= 0) {
      setError('Iznos mora biti veći od 0');
      return;
    }

    setIsLoading(true);

    try {
      const payload = {
        ...formData,
        iznos: iznosNum,
        korisnikId: formData.korisnikId || 1,
      };

      const response = await axios.post('http://localhost:8081/donacije/init-stripe', payload);

      if (response.data.success && response.data.paymentUrl) {
        window.location.href = response.data.paymentUrl;
      } else {
        throw new Error(response.data.message || 'Neuspješno kreiranje donacije');
      }
    } catch (err) {
      setError(err.response?.data?.message || err.message || 'Došlo je do greške pri donaciji');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="donation-container">
      {/* Hero Header */}

      {/* Donation Form */}
      <div className="form-card">
        {/* Form Header */}
        <div className="form-header">
          <h2 className="form-title">Vaša donacija - njihova sreća</h2>
        </div>

        {/* Form Content */}
        <div className="form-content">
          {error && (
            <div className="error-message">
              <p>{error}</p>
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-6">
            {/* IZNOS */}
            <div className="form-group">
              <label htmlFor="iznos" className="form-label">
                Iznos donacije (BAM)
              </label>
              <input
                type="number"
                id="iznos"
                name="iznos"
                min="0.01"
                step="0.01"
                value={formData.iznos}
                onChange={handleChange}
                required
                className="form-input"
                placeholder="0.00"
              />
            </div>

            {/* VRSTA DONACIJE */}
            <div className="form-group">
              <label htmlFor="vrstaDonacije" className="form-label">
                Reci nam za šta želiš da iskoristimo tvoju donaciju
              </label>
              <select
                id="vrstaDonacije"
                name="vrstaDonacije"
                value={formData.vrstaDonacije}
                onChange={handleChange}
                className="form-input"
              >
               {/* <option value="novčana">💸 Novčana donacija</option> */}
                <option value="hrana">🍖 Hrana za životinje</option>
                <option value="oprema">🧸 Oprema i igračke</option>
                <option value="veterinarske usluge">🩺 Veterinarske usluge</option>
              </select>
            </div>

            {/* OPIS */}
            <div className="form-group">
              <label htmlFor="opisDonacije" className="form-label">
                Dodatna napomena (opcionalno)
              </label>
              <textarea
                id="opisDonacije"
                name="opisDonacije"
                value={formData.opisDonacije}
                onChange={handleChange}
                rows={3}
                className="form-input"
                placeholder=""
              />
            </div>

            {/* SUBMIT BUTTON */}
            <button
              type="submit"
              disabled={isLoading}
              className="submit-button"
            >
              {isLoading ? (
                <span className="flex items-center justify-center">
                  <svg className="spinner -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  Obrada...
                </span>
              ) : (
                'Doniraj putem Stripe-a'
              )}
            </button>
          </form>

          <div className="footer-text">
            
            <Link to="/" className="back-link">
              ← Nazad na početnu
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DonationForm;