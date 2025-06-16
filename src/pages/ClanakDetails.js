// ClanakDetails.js
import React from 'react';
import Sidebar from '../components/Sidebar';
import './Details.css';

const ClanakDetails = () => {
    const clanak = {
        title: 'Značaj pravilne ishrane kućnih ljubimaca',
        author: 'Emira',
        content: `Pravilna ishrana kućnih ljubimaca značajno utiče na njihovo zdravlje, energiju i životni vijek.
    Treba se posavjetovati sa veterinarom prilikom izbora hrane, uzimajući u obzir uzrast, vrstu i aktivnost ljubimca.
    Industrijska hrana može biti praktična, ali je važno provjeriti sastav i kvalitet. Suplementi se ne preporučuju bez nadzora.`,
    };

    const moreArticles = [
        { title: 'Opasnosti loše ishrane', link: '/clanak/3' },
        { title: 'Hrana za mačke – preporuke', link: '/clanak/4' },
    ];

    return (
        <div className="details-container">
            <div className="main-content">
                <h2 className="details-title">{clanak.title}</h2>
                <p className="details-author"><strong>Autor:</strong> {clanak.author}</p>
                <p className="details-body">{clanak.content}</p>
            </div>

            <Sidebar title="Slični članci" items={moreArticles} />
        </div>
    );
};

export default ClanakDetails;
