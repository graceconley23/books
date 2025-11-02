import BookCard from "./BookCard.jsx";

import axios from 'axios';
import { useEffect, useState } from 'react';

function Cart() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);

  async function fetchBooks() {
    try {
      const cart = await axios.get(`http://localhost:8080/account/2`).then(response => response.data);
      console.log(cart);
      console.log(Array.isArray(cart));
      setBooks(cart);
    } catch (error) {
      console.log(error);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => { // call once on startup
    fetchBooks();
  }, [])

  if (loading) return <p>Loading cart...</p>

  return ( 
    <div>
      {/* Main content area */}
      <div className="d-flex">
        {/* Left content: Cards */}
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
        {/* Right content: Order Summary */}
        <div className="p-3 border-end" style={{ width: "20rem", minHeight: "100vh" }}>
          <h4>Order Summary</h4>
        </div>
      </div>
    </div>
  );
}

export default Cart;