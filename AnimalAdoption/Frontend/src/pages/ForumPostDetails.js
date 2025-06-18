import React, { useEffect, useState } from 'react';
import Sidebar from '../components/Sidebar';
import '../components/Details.css';
import CommentSection from '../components/CommentSection';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const ForumPostDetails = () => {
    const { id } = useParams();
    const [post, setPost] = useState(null);
    const [comments, setComments] = useState([]);
    const [similarArticles, setSimilarArticles] = useState([]);

    useEffect(() => {
        // Dohvati trenutno prikazani post
        axios.get(`http://localhost:8080/forum-postovi/sa-autorom/${id}`)
            .then(res => setPost(res.data))
            .catch(err => console.error('Greška prilikom dohvata posta:', err));

        // Dohvati komentare vezane za taj post
        axios.get(`http://localhost:8080/forum-komentari/all-sa-autorom`)
            .then(res => {
                const relatedComments = res.data.filter(c => Number(c.forumPostId) === Number(id));
                setComments(relatedComments);
            })
            .catch(err => console.error('Greška prilikom dohvata komentara:', err));

        // Dohvati sve ostale postove osim trenutno prikazanog
        axios.get("http://localhost:8080/forum-postovi/all-sa-autorom")
            .then(res => {
                const ostali = res.data
                    .filter(p => Number(p.id) !== Number(id))
                    .map(p => ({
                        title: p.naslovTeme,
                        link: `/forum/post/${p.id}`
                    }));
                setSimilarArticles(ostali);
            })
            .catch(err => console.error('Greška prilikom dohvata ostalih postova:', err));
    }, [id]);

    const handleNewComment = (commentText) => {
        const userId = localStorage.getItem("userId");
        const newComment = {
            sadrzajKomentara: commentText,
            forumPostId: parseInt(id),
            korisnikID: userId ? userId : "0", // ako nije logovan, postavljamo 0 za guest
            datumKomentiranja: new Date().toISOString()
        };
        axios.post('http://localhost:8080/forum-komentari/create', newComment)
            .then(res => setComments(prev => [...prev, res.data]))
            .catch(err => console.error('Greška prilikom dodavanja komentara:', err));
    };



    if (!post) return <div>Učitavanje posta...</div>;

    return (
        <div className="details-container">
            <div className="main-content">
                <h2 className="details-title">{post.naslovTeme}</h2>
                <p className="details-author"><strong>Autor:</strong> {post.autor}</p>
                <p className="details-body">{post.sadrzajPosta}</p>

                <CommentSection comments={comments} onAddComment={handleNewComment} />
            </div>
            <Sidebar title="Pročitaj još" items={similarArticles} />
        </div>
    );
};

export default ForumPostDetails;
