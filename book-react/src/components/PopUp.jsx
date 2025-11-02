import React from 'react';

function PopUp({showPopup, closePopup, title, author, series, volume, genres, isbn}) {
    if (!showPopup) {
        return null;
    }

    return  (
        <div className='popup-overlay' style = {{
                position: "fixed",        // stays in place over everything
                top: 0,
                left: 0,
                width: "100vw",
                height: "100vh",
                backgroundColor: "rgba(0,0,0,0.5)", // semi-transparent background
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                zIndex: 1000
            }}>
            <div className='popup-content' onClick={e => e.stopPropagation()} style={{
                    backgroundColor: "white",
                    padding: "2rem",
                    borderRadius: "8px",
                    maxWidth: "700px",
                    width: "90%",
                    'box-shadow': "0px 5px 15px rgba(0,0,0,0.3)",
                    textAlign: "center"
                }}>
                    <h2>{title}</h2>
                    <br/>
                    <h3>{series} Volume #{volume}</h3>
                    <h5>By: {author}</h5>
                    <p>ISBN number: {isbn}</p>

                <button onClick={closePopup} variant="danger">Close</button>
            </div>
        </div>
    );
}

export default PopUp;