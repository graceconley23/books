import { Component } from "react";
import Button from "react-bootstrap/Button";
import InputGroup from "react-bootstrap/InputGroup";
import Form from 'react-bootstrap/Form';

function SearchBar({searchText, setSearchText, setSearchPressed}) {
    // handleChange = (event) => {
    //     this.setState({
    //         inputText: event.target.value
    //     });
    // }

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Prevent form submission
            alertSearch(searchText);
        }
    };

    const alertSearch = (text) => {
        //alert("Hitched Enter! You searched for: " + text);
        setSearchPressed(true);
    };
    
        return (
            <Form>
                <Form.Label>Search the Catalog</Form.Label>
                <InputGroup className="mb-3">
                    <Form.Control
                        id="search-input"
                        placeholder="Search by title, author, or series"
                        onKeyDown={handleKeyPress}
                        value={searchText}
                        onChange={e => setSearchText(e.target.value)}
                    />
                    <Button variant="secondary" onClick={() => alertSearch(searchText)}>
                        Search
                    </Button>
                </InputGroup>
        </Form>
        );
}

export default SearchBar;