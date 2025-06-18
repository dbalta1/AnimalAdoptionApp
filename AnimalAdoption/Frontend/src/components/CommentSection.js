import React, { useState } from 'react';
import './Details.css';

const CommentSection = ({ comments, onAddComment }) => {
    const [newComment, setNewComment] = useState('');

    const handleSubmit = () => {
        if (newComment.trim()) {
            onAddComment(newComment);
            setNewComment('');
        }
    };

    return (
        <div className="comment-section">
            <h3>Komentari</h3>
            <ul className="comment-list">
                {comments.map((comment, index) => (
                    <li key={index}>
                        <strong>{comment.autor || 'Gost'}:</strong> {comment.sadrzajKomentara}
                    </li>
                ))}
            </ul>
            <textarea
                className="comment-textarea"
                placeholder="Dodaj komentar..."
                value={newComment}
                onChange={(e) => setNewComment(e.target.value)}
            />
            <button className="comment-button" onClick={handleSubmit}>Pošalji</button>
        </div>
    );
};

export default CommentSection;
