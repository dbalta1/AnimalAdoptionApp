// ClanakDetails.js
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Sidebar from '../components/Sidebar';
import './Details.css';

const ClanakDetails = () => {
    const { id } = useParams(); // dohvatimo id iz URL-a
    const [clanak, setClanak] = useState(null);
    const [moreArticles, setMoreArticles] = useState([]);

    useEffect(() => {
        // Dohvati jedan članak po ID-u
        axios.get(`http://localhost:8080/clanci/get/${id}`)
            .then(res => {
                setClanak(res.data);
            })
            .catch(err => {
                console.error('Greška prilikom dohvata članka:', err);
            });

        // Dohvati sve članke za sidebar (osim trenutnog)
        axios.get('http://localhost:8080/clanci/all')
            .then(res => {
                const drugiClanci = res.data.filter(c => c.id !== parseInt(id));
                const formatirano = drugiClanci.map(c => ({
                    title: c.naslov,
                    link: `/clanak/${c.id}`
                }));
                setMoreArticles(formatirano);
            })
            .catch(err => {
                console.error('Greška prilikom dohvata sličnih članaka:', err);
            });
    }, [id]);

    if (!clanak) return <p>Učitavanje članka...</p>;

    return (
        <div className="details-container">
            <div className="main-content">
                <h2 className="details-title">{clanak.naslov}</h2>
                <p className="details-author"><strong>Autor:</strong> {clanak.autor}</p>
                <p className="details-body">{clanak.sadrzaj}</p>
            </div>

            <Sidebar title="Slični članci" items={moreArticles} />
        </div>
    );
};

export default ClanakDetails;
