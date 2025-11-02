import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import React, { useState } from 'react';
import PopUp from './PopUp';
import axios from "axios";

function BookshelfCard({title, author, series, volume, cover, genres, isbn, userId}) {
    const [showPopup, setShowPopup] = useState(false);

    const togglePopupOn = () => {
        setShowPopup(true);
    }

    const togglePopupOff = () => {
        setShowPopup(false);
    }

    return (
        <div style={{'box-shadow': '5px 5px 10px rgba(0, 0, 0, 0.3)'}}>
            <Card style={{ width: "16rem" }} onClick={togglePopupOn}>
                
            <Card.Img 
                variant="top"
                style={{ height: "250px", objectFit: "contain", width: "100%" }}
                src={cover}
            />
            </Card>
            <PopUp showPopup={showPopup} closePopup={togglePopupOff} title={title} author={author}
            series={series} volume={volume} genres={genres} isbn={isbn}>
                <h1>PopUp!</h1>
            </PopUp>
            <div className="d-grid gap-2">
                <Card.Footer variant="outline-primary" size="sm" className="text-center">
                    <small>{title}</small>
                </Card.Footer>
            </div>
        </div>
    );
}

export default BookshelfCard;