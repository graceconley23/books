import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import Card from 'react-bootstrap/Card';

import { Component } from "react";

class BookCartCard extends Component {
  render() {

    const { title, author, cover, price, quantity } = this.props;

    const formatPrice = (val) =>
      val.toLocaleString("en-US", { style: "currency", currency: "USD", maximumFractionDigits: 2 });

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
              <Button variant="outline-danger">-</Button>
              <Button variant="outline-dark" disabled="true">
                Qty: {quantity}
              </Button>
              <Button variant="outline-success">+</Button>
            </ButtonGroup>
          </div>
        </Card.Body>
      </Card>
    );
  }
}

export default BookCartCard;