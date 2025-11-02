import Button from 'react-bootstrap/Button';
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';

import { BrowserRouter, Routes, Route, useNavigate, Navigate } from 'react-router-dom';
import { useState } from 'react';

import './App.css';
import SearchBar from './components/SearchBar.jsx';
import BookCard from './components/BookCard.jsx';

function NavigationBar() {
  const navigate = useNavigate();

  return (
    <div className="NavigationBar">
      <h1 className="important-text">Bookworm Books</h1>
    <Tabs
      defaultActiveKey="bookshelf"
      id="book-tabs"
      className="mb-3"
      justify
      onSelect={(k) => navigate(`/${k}`)}
    >
      <Tab eventKey="bookshelf" title="My Bookshelf" />
      <Tab eventKey="catalog" title="Catalog" />
      <Tab eventKey="account" title="Account" />
    </Tabs>
    </div>
  );
}

function Bookshelf() {
  const [count, setCount] = useState(0);

  function increaseCount() {
    setCount(count + 1);
  }

  return (
    <div className="d-grid gap-2">
      <h1>Welcome Bookworms!</h1>
      <Button variant="outline-success" size="lg" onClick={increaseCount}>
        Read!
      </Button>
      <h2>You’ve read {count} times this year</h2>
    </div>
  )
}

function Catalog() {
  return ( 
    <div>
      <SearchBar placeholder = "Search by book, author, or series..."/>
      <div className="d-flex flex-wrap justify-content-left gap-4 p-4">
        <BookCard 
          title="The Lightning Thief" 
          author="Rick Riordan" 
          series="Percy Jackson & the Olympians"
          volume="1"
          cover="/vite.svg"
        />
        <BookCard 
          title="Inkheart" 
          author="Cornelia Funke" 
          series="Inkheart Trilogy"
          volume="1"
          cover="src/assets/react.svg"
        />
      </div>
    </div>
  );
}

function Account() {
  return (
    <Button variant="danger" size="sm">
      Logout
    </Button>
  );
}

function App() {
  return (
    <BrowserRouter>
      <NavigationBar />

      {/* Routes */}
      <Routes>
        <Route path="*" element={<Navigate to="/bookshelf" replace />} /> {/* default route */}
        <Route path="/bookshelf" element={<Bookshelf />} />
        <Route path="/catalog" element={<Catalog />} />
        <Route path="/account" element={<Account />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;