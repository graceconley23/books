import BookCartCard from "./BookCartCard.jsx";

import axios from 'axios';
import { useEffect, useState } from 'react';

function Cart({ setCartQuantity }) {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [userId, setUserId] = useState(2); // hardcoded for now

  const formatPrice = (val) =>
    val.toLocaleString("en-US", { style: "currency", currency: "USD", maximumFractionDigits: 2 });

  async function fetchBooks() {
    try {
      const cart = await axios.get(`http://localhost:8080/account/cart/${userId}`).then(response => response.data);
      setBooks(cart);
    } catch (error) {
      console.log(error);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => { // call once on startup
    fetchBooks();
    setUserId(2); // hardcoded for now
  }, [])

  const quantity = books.reduce((sum, book) => sum + (book.quantity), 0);
  const subtotal = books.reduce((sum, book) => sum + (book.price * book.quantity), 0);
  const tax = subtotal * 0.08;
  const shipping = quantity > 0 ? 5.99 : 0;
  const total = subtotal + tax + shipping;

  useEffect(() => {
    setCartQuantity(quantity);
  }, [quantity, setCartQuantity]);

  if (loading) return <p>Loading cart...</p>

  return ( 
    <div>
      {/* Main content area */}
      <div className="d-flex">
        {/* Left content: Cards */}
        <div className="flex-grow-1 p-4">
          <h2>Your Order ({quantity} item{quantity !== 1 ? "s" : ""})</h2>
          <br></br>
          <div className="d-flex flex-wrap justify-content-start gap-4">
            {books.map((book, index) => (
              <BookCartCard
                key={index}
                title={book.title}
                author={book.author}
                cover={book.coverImageUrl}
                price={book.price}
                quantity={book.quantity}
                isbn={book.isbn}
                userId={userId}
                refreshCart={fetchBooks}
              />
            ))}
          </div>
        </div>
        {/* Right content: Order Summary */}
        <div className="p-3 border-end" style={{ width: "20rem", minHeight: "100vh" }}>
          <h4>Order Summary</h4>
          <br></br>
          <p>Subtotal: {formatPrice(subtotal)}</p>
          <p>Tax: {formatPrice(tax)}</p>
          <p>Shipping: {formatPrice(shipping)}</p>
          <hr />
          <p>Total: {formatPrice(total)}</p>
          <button className="btn btn-success w-100">Checkout</button>
        </div>
      </div>
    </div>
  );
}

export default Cart;