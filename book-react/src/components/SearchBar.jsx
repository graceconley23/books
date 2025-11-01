import { Component } from "react";
import Form from 'react-bootstrap/Form';

class SearchBar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            inputText: ""
        };
    }
    
    render() {
        return (
            <Form>
                <Form.Group className="mb-3" controlId="my-search-bar">
                <Form.Label>Search the Catalog</Form.Label>
                <Form.Control placeholder="Search by title, author, or series" />
                </Form.Group>
            </Form>
        );
    }
}

export default SearchBar;