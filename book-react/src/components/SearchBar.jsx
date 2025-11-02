import { Component } from "react";
import Button from "react-bootstrap/Button";
import InputGroup from "react-bootstrap/InputGroup";
import Form from 'react-bootstrap/Form';

class SearchBar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            inputText: ""
        };
    }

    handleChange = (event) => {
        this.setState({
            inputText: event.target.value
        });
    }

    handleKeyPress = (event) => {
        if (event.key === 'Enter') {
             event.preventDefault(); // Prevent form submission
            this.alertSearch(this.state.inputText);
        }
    };

    alertSearch = (text) => {
        alert("Hitched Enter! You searched for: " + text);
    };
    
    render() {
        return (
            <Form>
                <Form.Label>Search the Catalog</Form.Label>
                <InputGroup className="mb-3">
                    <Form.Control
                        id="search-input"
                        placeholder="Search by title, author, or series"
                        onKeyDown={this.handleKeyPress}
                        onChange={this.handleChange}
                    />
                    <Button variant="secondary" onClick={() => this.alertSearch(this.state.inputText)}>
                        Search
                    </Button>
                </InputGroup>
        </Form>
        );
    }
}

export default SearchBar;