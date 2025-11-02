import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import axios from "axios";
import { useState } from "react";

function BookCard({title, author, series, volume, cover, price, isbn, userId, refreshCartCount}) {

  const [updating, setUpdating] = useState(false);

  const formatPrice = (val = 0) =>
    val.toLocaleString("en-US", { style: "currency", currency: "USD", maximumFractionDigits: 2 });

    const addToCart = async () => {
    if (!userId || !isbn) return;

    setUpdating(true);
    try {
      await axios.put(`http://localhost:8080/account/cart/${userId}/${isbn}`);
      await refreshCartCount();
    } catch (err) {
      console.error("Failed to update cart:", err);
    } finally {
      setUpdating(false);
    }
  };
  
  return (
    <Card style={{ width: "16rem" }}>
      <Card.Img 
        variant="top"
        style={{ height: "200px", objectFit: "contain", width: "100%" }}
        src={cover}
      />
      <Card.Body>
        <Card.Title>{title}</Card.Title>
        <Card.Text>
          By: {author}
          <br />
          Series: {series} (Book {volume})
          <br />
          Price: {formatPrice(price)}
        </Card.Text>

        <div className="d-grid gap-2">
          <Button variant="outline-primary" size="sm" className="w-100"
            onClick={() => alert("Added to Bookshelf")}
          >
            Add to Bookshelf
          </Button>
          <Button variant="outline-primary" size="sm" className="w-100"
            onClick={addToCart}
            disabled={updating}
          >
            Add to Cart
          </Button>
        </div>
      </Card.Body>
    </Card>
  );
}

export default BookCard;