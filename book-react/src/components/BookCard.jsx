import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

import { Component } from "react";

class BookCard extends Component {
  render() {

    const { title, author, series, volume, cover } = this.props;

    return (
      <Card style={{ width: "17rem" }}>
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
          </Card.Text>

          <div className="d-grid gap-2">
            <Button variant="outline-primary" size="sm" className="w-100">
              Add to Bookshelf
            </Button>
            <Button variant="outline-primary" size="sm" className="w-100">
              Add to Cart
            </Button>
          </div>
        </Card.Body>
      </Card>
    );
  }
}

export default BookCard;