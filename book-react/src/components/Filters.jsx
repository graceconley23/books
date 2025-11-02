import Accordion from 'react-bootstrap/Accordion';
import Form from 'react-bootstrap/Form';
import Range from './Range.jsx';

function Filters({viewingAvailable, setViewingAvailable, maxPrice, setMaxPrice, genres, selectedGenres, setSelectedGenres}) {
  const handleAvailabilityChange = (event) => {
    setViewingAvailable(event.target.checked)
  }
  function handleNewGenre(genre) {
    if (selectedGenres.includes(genre)) {
      selectedGenres.splice(selectedGenres.indexOf(genre), 1); // remove the genre
    } else {
      selectedGenres.push(genre);
    }
    setSelectedGenres(selectedGenres);
  }
  return (
    <Accordion alwaysOpen>
      <Accordion.Item eventKey="price-range">
        <Accordion.Header>Price Range</Accordion.Header>
        <Accordion.Body>
          <Range max={100} setMaxPrice={setMaxPrice}/>
        </Accordion.Body>
      </Accordion.Item>
      <Accordion.Item eventKey="availability">
        <Accordion.Header>Availability</Accordion.Header>
        <Accordion.Body>
          <Form>
            <div className="mb-3">
                <Form.Check // prettier-ignore
                    type="switch"
                    id="in-stock-switch"
                    label={"In Stock"}
                    checked={viewingAvailable}
                    onChange={handleAvailabilityChange}
                />
            </div>
          </Form>
        </Accordion.Body>
      </Accordion.Item>
      <Accordion.Item eventKey="genre">
        <Accordion.Header>Genre</Accordion.Header>
        <Accordion.Body>
          <Form>
            <div className="mb-3">
                {genres.map((genre, index) => (
                  <Form.Check // prettier-ignore
                    key={index}
                    type="checkbox"
                    id="young-adult-checkbox"
                    label={genre}
                    onChange={() => {handleNewGenre(genre)}} // is there a better way to do this ???
                />
                ))}
                {/* <Form.Check // prettier-ignore
                    type="checkbox"
                    id="young-adult-checkbox"
                    label={"Young Adult"}
                />
                <Form.Check // prettier-ignore
                    type="checkbox"
                    id="fantasy-checkbox"
                    label={"Fantasy"}
                /> */}
            </div>
          </Form>
        </Accordion.Body>
      </Accordion.Item>
    </Accordion>
  );
}

export default Filters;