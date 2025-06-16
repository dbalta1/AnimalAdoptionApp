import React, { useEffect, useState } from 'react';
import './Forum.css'; // koristi tvoj postojeći stil

const Forum = () => {
    const [posts, setPosts] = useState([]);
    const [clanci, setClanci] = useState([]);

    // Test podaci (mockovi)
    useEffect(() => {
        setPosts([
            {
                id: 1,
                naslov: "Kako pravilno udomiti psa?",
                autor: "Emira",
                sadrzaj: "Udomljavanje je plemenit čin, ali treba se dobro pripremiti."
            },
            {
                id: 2,
                naslov: "Hrana za mačke – preporuke?",
                autor: "Berin",
                sadrzaj: "Koju hranu vi dajete svojim mačkama? Suha vs mokra?"
            },
            {
                id: 3,
                naslov: "Vakcinacija kućnih ljubimaca",
                autor: "Tarik",
                sadrzaj: "Da li vakcinišete pse u privatnim ili državnim ambulantama?"
            }
        ]);

        setClanci([
            {
                id: 101,
                naslov: "Zašto je sterilizacija važna?",
                opis: "Saznajte kako sterilizacija može smanjiti broj napuštenih životinja..."
            },
            {
                id: 102,
                naslov: "Kako naučiti psa osnovnim komandama?",
                opis: "Praktični vodič za obuku pasa početnicima."
            }
        ]);
    }, []);

    return (
        <div className="forum-container">
            <div className="posts-section">
                <h2>🗨️ Forum postovi</h2>
                {posts.map(post => (
                    <div className="post-card" key={post.id}>
                        <h3>
                            <a href={`/forum/post/${post.id}`}>{post.naslov}</a>
                        </h3>
                        <p><strong>Autor:</strong> {post.autor}</p>
                        <p>{post.sadrzaj}</p>
                    </div>
                ))}
            </div>

            <div className="sidebar">
                <h3>📚 Edukativni članci</h3>
                {clanci.map(clanak => (
                    <div className="clanak-preview" key={clanak.id}>
                        <h4>
                            <a href={`/clanak/${clanak.id}`}>{clanak.naslov}</a>
                        </h4>
                        <p>{clanak.opis}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Forum;
