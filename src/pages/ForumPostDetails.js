// ForumPostDetails.js
import React from 'react';
import Sidebar from '../components/Sidebar';
import '../components/Details.css'; // popravljeno da ide iz components
import CommentSection from '../components/CommentSection';

const ForumPostDetails = () => {
    const post = {
        title: 'Kako pravilno udomiti psa?',
        author: 'Emira',
        content: `Udomljavanje je plemenit čin, ali treba se dobro pripremiti. 
        Potrebno je obezbijediti sigurno okruženje, redovno voditi ljubimca veterinaru 
        i osigurati dovoljno ljubavi.`,
    };

    const similarArticles = [
        { title: '5 savjeta za udomljavanje', link: '/clanak/1' },
        { title: 'Psihologija pasa', link: '/clanak/2' },
    ];

    return (
        <div className="details-container">
            <div className="main-content">
                <h2 className="details-title">{post.title}</h2>
                <p className="details-author"><strong>Autor:</strong> {post.author}</p>
                <p className="details-body">{post.content}</p>

                {/* Komentari */}
                <CommentSection />
            </div>

            {/* Desni sidebar */}
            <Sidebar title="Pročitaj još" items={similarArticles} />
        </div>
    );
};

export default ForumPostDetails;
