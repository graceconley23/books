import Accordion from 'react-bootstrap/Accordion';
import Form from 'react-bootstrap/Form';

function Filters() {
  return (
    <Accordion alwaysOpen>
      <Accordion.Item eventKey="price-range">
        <Accordion.Header>Price Range</Accordion.Header>
        <Accordion.Body>
          Filter goes here
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
                <Form.Check // prettier-ignore
                    type="checkbox"
                    id="young-adult-checkbox"
                    label={"Young Adult"}
                />
                <Form.Check // prettier-ignore
                    type="checkbox"
                    id="fantasy-checkbox"
                    label={"Fantasy"}
                />
            </div>
          </Form>
        </Accordion.Body>
      </Accordion.Item>
    </Accordion>
  );
}

export default Filters;