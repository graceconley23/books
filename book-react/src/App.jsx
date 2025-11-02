import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';
import Badge from 'react-bootstrap/Badge';

import axios from 'axios';

import { BrowserRouter, Routes, Route, useNavigate, Navigate } from 'react-router-dom';
import { useEffect, useState } from 'react';

import './App.css';
import SearchBar from './components/SearchBar.jsx';
import BookCard from './components/BookCard.jsx';
import Filters from './components/Filters.jsx';
import Cart from './components/Cart.jsx';
import Bookshelf from './components/Bookshelf.jsx';

function NavigationBar({quantity}) {
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
      <Tab eventKey="account" title={<>
        Account <Badge bg="secondary">{quantity}</Badge>
      </>
      }/>
    </Tabs>
    </div>
  );
}

function Catalog({currentUser, setCurrentUser, setCartQuantity}) {
  const [books, setBooks] = useState([]);
  const [cart, setCart] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchText, setSearchText] = useState("");
  const [searchPressed, setSearchPressed] = useState(false);
  const [viewingAvailable, setViewingAvailable] = useState(false);
  const [maxPrice, setMaxPrice] = useState(0);
  const [genres, setGenres] = useState([])
  const [selectedGenres, setSelectedGenres] = useState([]);

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

  async function fetchCart() {
    try {
      const cart = await axios.get(`http://localhost:8080/account/cart/${currentUser}`).then(response => response.data);
      setCart(cart);
    } catch (error) {
      console.log(error);
    } finally {
      setLoading(false);
    }
  }

  const quantity = cart.reduce((sum, book) => sum + (book.quantity), 0);
  useEffect(() => {
    setCartQuantity(quantity);
  }, [quantity, setCartQuantity]);

  useEffect(() => { // call once on startup
    setCurrentUser(2); // hardcoded for now
    fetchBooks();
    loadGenres();
    fetchCart();
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
              <BookCard setCartQuantity={setCartQuantity}
                key={index}
                title={book.title}
                author={book.author}
                series={book.series}
                volume={book.numberInSeries}
                cover={book.coverImageUrl}
                price={book.price}
                isbn={book.isbn}
                userId={currentUser}
                refreshCartCount={fetchCart}
              />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

function Account({ setCartQuantity }) {
  return (
    <Cart setCartQuantity={setCartQuantity}/>
  );
}

function App() {
  const [cartQuantity, setCartQuantity] = useState(0);
    const [currentUser, setCurrentUser] = useState(2);

  return (
    <BrowserRouter>
      <NavigationBar quantity={cartQuantity} />

      {/* Routes */}
      <Routes>
        <Route path="*" element={<Navigate to="/bookshelf" replace />} /> {/* default route */}
        <Route path="/bookshelf" element={<Bookshelf currentUser={currentUser}/>} />
        <Route path="/catalog" element={<Catalog currentUser={currentUser} setCurrentUser={setCurrentUser} setCartQuantity={setCartQuantity}/>} />
        <Route path="/account" element={<Account setCartQuantity={setCartQuantity}/>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;