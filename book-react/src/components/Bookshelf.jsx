import Button from "react-bootstrap/Button";
import { useState, useEffect } from 'react';
import axios from 'axios';

function Bookshelf({currentUser}) {
  const [count, setCount] = useState(0);

  useEffect(() => {
    let url = `http://localhost:8080/account/timesRead/${currentUser}`
    const timesRead = axios.get(url).then(response => response.data);
    setCount(timesRead);
  }, [])

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

  return (
    <div className="d-grid gap-2">
      <h1>Welcome Bookworms!</h1>
      <Button variant="outline-success" size="lg" onClick={increaseCount}>
        Read!
      </Button>
      <Button variant="danger" size="lg" onClick={decreaseCount}>
        Unread!
      </Button>
      <h2>You’ve read {count} times this year</h2>
    </div>
  )
}

export default Bookshelf;