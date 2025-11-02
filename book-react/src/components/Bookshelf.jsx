import Button from "react-bootstrap/Button";
import { useState, useEffect } from 'react';
import BookshelfCard from './BookshelfCard.jsx';
import axios from 'axios';

function Bookshelf({currentUser}) {
  const [count, setCount] = useState(0);
  const [books, setBooks] = useState([0]);

  useEffect(() => {
    setupBookshelf();
  }, [])

  useEffect(() => {
    console.log(books);
  }, [books])

  function increaseCount() {
    let url = `http://localhost:8080/account/timesRead/${currentUser}/1`;
    const timesRead = axios.put(url).then(response => response.data);
    setCount(timesRead);
  }

  function decreaseCount() {
    let url = `http://localhost:8080/account/timesRead/${currentUser}/-1`;
    const timesRead = axios.put(url).then(response => response.data);
    setCount(timesRead);
  }

  async function setupBookshelf() {
    let url = `http://localhost:8080/account/timesRead/${currentUser}`;
    const timesRead = await axios.get(url).then(response => response.data);
    setCount(timesRead);
    url = `http://localhost:8080/account/bookshelf/${currentUser}`;
    const bookshelf = await axios.get(url).then(response => response.data);
    setBooks(bookshelf);
  }

  if (books.length === 1 && books[0] === 0) { // empty bookshelf shouldn't do this
    <p>Loading bookshelf...</p>
  }

  return (
    <div>
        <h2>Welcome Bookworms!</h2>
        <div className="flex-grow-1 p-4">
            <div className="d-flex flex-wrap justify-content-start gap-4">
                {books.map((book, index) => (
                    <BookshelfCard
                        key={index}
                        title={book.title}
                        author={book.author}
                        series={book.series}
                        volume={book.numberInSeries}
                        cover={book.coverImageUrl}
                        isbn={book.isbn}
                        userId={currentUser}
                    />
                ))}
            </div>
            <div style={{ padding: '20px 10px' }}>
                <Button variant="outline-success" size="lg" onClick={increaseCount}>
                    Read!
                </Button>
                <Button variant="danger" style={{margin: '0px 10px'}} size="lg" onClick={decreaseCount}>
                    Unread!
                </Button>
            </div>
            <h2 >You’ve read {count} times this year!</h2>
        </div>  
    </div> 
  )
}

export default Bookshelf;