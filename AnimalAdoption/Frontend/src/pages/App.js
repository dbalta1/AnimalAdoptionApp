import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AppNavbar from './components/Navbar';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import AnimalProfile from './pages/AnimalProfile';
import Animals from './pages/Animals';
import About from './pages/About'; 
import DonationForm from './pages/DonationForm'
import Forum from "./pages/Forum";
import ForumPostDetails from './pages/ForumPostDetails';
import ClanakDetails from './pages/ClanakDetails';


import './App.css';

function App() {
  return (
    <Router>
      <AppNavbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/animals" element={<Animals />} />
        <Route path="/about" element={<About />} />
        <Route path="/donate" element={<DonationForm />} />
        <Route path="/animals/:id" element={<AnimalProfile />} />
          <Route path="/forum" element={<Forum />} />
          <Route path="/forum" element={<Forum />} />
          <Route path="/forum/post/:id" element={<ForumPostDetails />} />
          <Route path="/clanak/:id" element={<ClanakDetails />} />
      </Routes>
    </Router>
  );
}

export default App;