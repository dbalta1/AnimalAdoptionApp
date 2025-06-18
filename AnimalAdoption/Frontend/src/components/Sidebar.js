// Sidebar.js
import React from 'react';
import './Details.css';

const Sidebar = ({ items, title }) => {
    return (
        <div className="sidebar">
            <h4>{title}</h4>
            <ul>
                {items.map((item, index) => (
                    <li key={index}><a href={item.link}>{item.title}</a></li>
                ))}
            </ul>
        </div>
    );
};

export default Sidebar;
