import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';
import axios from 'axios';

import { BrowserRouter, Routes, Route, useNavigate, Navigate } from 'react-router-dom';
import { useEffect, useState } from 'react';

import './App.css';
import SearchBar from './components/SearchBar.jsx';
import BookCard from './components/BookCard.jsx';
import Filters from './components/Filters.jsx';
import Cart from './components/Cart.jsx';
import Bookshelf from './components/Bookshelf.jsx';


function NavigationBar() {
  const navigate = useNavigate();
  const currentPath = location.pathname.replace("/", "") || "bookshelf";

  return (
    <div className="NavigationBar">
      <h1 className="important-text">Bookworm Books</h1>
    <Tabs
      defaultActiveKey={currentPath}
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

function Catalog() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchText, setSearchText] = useState("");
  const [searchPressed, setSearchPressed] = useState(false);
  const [viewingAvailable, setViewingAvailable] = useState(false);
  const [maxPrice, setMaxPrice] = useState(0);
  const [genres, setGenres] = useState([])
  const [selectedGenres, setSelectedGenres] = useState([]);
  const [currentUser, setCurrentUser] = useState(0);

  async function fetchBooks() {
    try {
      let url = `http://localhost:8080/catalog/?text=${encodeURIComponent(searchText)}&maxPrice=${encodeURIComponent(maxPrice)}&checkStock=${encodeURIComponent(viewingAvailable)}`
      for (let i = 0; i < selectedGenres.length; i++) {
        url += '&genres=' + selectedGenres[i]
      }
      const catalog = await axios.get(url).then(response => response.data);
      console.log(url)
      setBooks(catalog);
    } catch (error) {
      console.log(error);
    } finally {
      setLoading(false);
    }
  }

  async function loadGenres() {
    const genres = await axios.get('http://localhost:8080/catalog/genres').then(response => response.data);
    setGenres(genres)
  }

  useEffect(() => { // call once on startup
    fetchBooks();
    loadGenres();
    setCurrentUser(2);
  }, [])

  useEffect(() => {
    fetchBooks();
    setSearchPressed(false);
  }, [searchPressed])

  if (loading) return <p>Loading catalog...</p>

  return ( 
    <div>
      {/* Search bar full width at top */}
      <div className="p-3 border-bottom bg-light">
        <SearchBar placeholder="Search by book, author, or series..." searchText={searchText} setSearchText={setSearchText}
        setSearchPressed={setSearchPressed}/>
      </div>

      {/* Main content area */}
      <div className="d-flex">
        {/* Left sidebar: Filters */}
        <div className="p-3 border-end" style={{ width: "20rem", minHeight: "100vh" }}>
          <Filters viewingAvailable={viewingAvailable} setViewingAvailable={setViewingAvailable}
          maxPrice={maxPrice} setMaxPrice={setMaxPrice} genres={genres} selectedGenres={selectedGenres}
          setSelectedGenres={setSelectedGenres}/>
        </div>

        {/* Right content: Cards */}
        <div className="flex-grow-1 p-4">
          <div className="d-flex flex-wrap justify-content-start gap-4">
            {books.map((book, index) => (
              <BookCard
                key={index}
                title={book.title}
                author={book.author}
                series={book.series}
                volume={book.numberInSeries}
                cover={book.coverImageUrl}
              />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

function Account() {
  return (
    <Cart />
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