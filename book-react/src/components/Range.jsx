import { useState } from "react";
import Form from "react-bootstrap/Form";

function Range({ min = 0, max }) {
  const [value, setValue] = useState(max);

  const formatPrice = (val) =>
    val.toLocaleString("en-US", { style: "currency", currency: "USD", maximumFractionDigits: 0 });

  const handleChange = (e) => {
    const newVal = Number(e.target.value);
    setValue(newVal);
  }

  return (
    <div className="p-3">
      <div className="d-flex justify-content-between">
        <Form.Label>Max:</Form.Label>
        <span>{formatPrice(value)}</span>
      </div>
      <Form.Range
        min={min}
        max={max}
        value={value}
        onChange={(e) => handleChange(e)}
      />
    </div>
  );
}

export default Range;