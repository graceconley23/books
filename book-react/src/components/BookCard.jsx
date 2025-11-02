import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import reactLogo from '../assets/react.svg';

import { Component } from "react";

class BookCard extends Component {
  render() {
    return (
      <Card style={{ width: "18rem" }}>
        <Card.Img variant="top" src={reactLogo} />
        <Card.Body>
          <Card.Title>Book Title</Card.Title>
          <Card.Text>
            Author Name
            <br />
            Series Name (Volume #)
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