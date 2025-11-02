import Button from "react-bootstrap/Button";
import { useState } from 'react';

function Bookshelf({currentUser}) {
  const [count, setCount] = useState(0);

  function increaseCount() {
    setCount(count + 1);
  }

  return (
    <div className="d-grid gap-2">
      <h1>Welcome Bookworms!</h1>
      <Button variant="outline-success" size="lg" onClick={increaseCount}>
        Read!
      </Button>
      <h2>You’ve read {count} times this year</h2>
    </div>
  )
}

export default Bookshelf;