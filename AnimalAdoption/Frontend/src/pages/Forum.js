// Forum.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Forum.css';

const Forum = () => {
    const [posts, setPosts] = useState([]);
    const [clanci, setClanci] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8080/forum-postovi/all-sa-autorom")
            .then(response => setPosts(response.data))
            .catch(error => console.error("Greška kod postova:", error));

        axios.get("http://localhost:8080/clanci/all")
            .then(response => setClanci(response.data))
            .catch(error => console.error("Greška kod članaka:", error));
    }, []);

    return (
        <div className="forum-container">
            <div className="posts-section">
                <h2>🗨️ Forum postovi</h2>
                {posts.map(post => (
                    <div className="post-card" key={post.id}>
                        <h3><a href={`/forum/post/${post.id}`}>{post.naslovTeme}</a></h3>
                        <p><strong>Autor:</strong> {post.autor || "Nepoznat"}</p>
                        <p>{post.sadrzajPosta}</p>
                    </div>
                ))}
            </div>

            <div className="sidebar">
                <h3>📚 Edukativni članci</h3>
                {clanci.map(clanak => (
                    <div className="clanak-preview" key={clanak.id}>
                        <h4><a href={`/clanak/${clanak.id}`}>{clanak.naslov}</a></h4>
                        <p>{clanak.opis}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Forum;
