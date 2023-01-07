import React from "react";
import "../App.css"

function Item(props) {

    return(
        <div className="item-bg">
            <h4>{props.name}</h4>
            <div>
                <h4>Hinta</h4>
                <h4 className="title">{props.price}</h4>
            </div>
            <div>
                <h4>Varastossa</h4>
                <h4 className="title">{props.inventory}</h4>
            </div>
            <div>
                <button onClick={() => props.plusOne(props.id)}>+</button>
                <h5 className="title">{props.count}</h5>
                <button onClick={() => props.minusOne(props.id)}>-</button>
            </div>
        </div>
    );
}

export default Item;