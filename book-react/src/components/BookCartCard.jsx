import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import Card from "react-bootstrap/Card";
import axios from "axios";

function BookCartCard({ title, author, cover, price, quantity, isbn, userId, refreshCart }) {
  const [updating, setUpdating] = useState(false);

  const formatPrice = (val) =>
    val.toLocaleString("en-US", { style: "currency", currency: "USD", maximumFractionDigits: 2 });

  const addToCart = async () => {
    if (!userId || !isbn) return;

    setUpdating(true);
    try {
      await axios.put(`http://localhost:8080/account/cart/${userId}/${isbn}`);
      await refreshCart(); // fetch the latest cart from GET /account/{userId}
    } catch (err) {
      console.error("Failed to update cart:", err);
    } finally {
      setUpdating(false);
    }
  };

    const deleteFromCart = async () => {
    if (!userId || !isbn) return;

    setUpdating(true);
    try {
      await axios.delete(`http://localhost:8080/account/cart/${userId}/${isbn}`);
      await refreshCart(); // fetch the latest cart from GET /account/{userId}
    } catch (err) {
      console.error("Failed to delete from cart:", err);
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
          Price: {formatPrice(price)}
        </Card.Text>

        <div className="d-grid gap-2">
          <ButtonGroup aria-label="quantity">
            <Button
              variant="outline-danger"
              onClick={deleteFromCart}
              disabled={updating}
            >
              -
            </Button>
            <Button variant="outline-dark" disabled>
              Qty: {quantity}
            </Button>
            <Button
              variant="outline-success"
              onClick={addToCart}
              disabled={updating}
            >
              +
            </Button>
          </ButtonGroup>
        </div>
      </Card.Body>
    </Card>
  );
}

export default BookCartCard;
