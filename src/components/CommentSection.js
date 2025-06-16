import React, { useState } from 'react';
import './Details.css'; // koristi isti fajl za stil

const CommentSection = () => {
    const [comments, setComments] = useState([
        { author: 'Tarik', text: 'Odličan post!' },
        { author: 'Berin', text: 'Hvala na informacijama.' }
    ]);

    const [newComment, setNewComment] = useState('');

    const handleAddComment = () => {
        if (newComment.trim() !== '') {
            const newEntry = { author: 'Gost', text: newComment };
            setComments([...comments, newEntry]);
            setNewComment('');
        }
    };

    return (
        <div className="comment-section">
            <h3>Komentari</h3>
            <ul className="comment-list">
                {comments.map((comment, index) => (
                    <li key={index}>
                        <strong>{comment.author}:</strong> {comment.text}
                    </li>
                ))}
            </ul>
            <textarea
                placeholder="Dodaj komentar..."
                value={newComment}
                onChange={(e) => setNewComment(e.target.value)}
                className="comment-textarea"
            />
            <button onClick={handleAddComment} className="comment-button">
                Pošalji
            </button>
        </div>
    );
};

export default CommentSection;
