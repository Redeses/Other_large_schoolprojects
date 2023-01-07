import React from "react";
import Item from "./Item";

function ItemList(props) {
    return(
        <div className="list-bg">
            {props.itemList.map(item => (
                <Item key={item.id} id={item.id}
                name={item.name} price={item.price} inventory={item.inventory} count={item.count} 
                plusOne={props.plusOne}
                minusOne={props.minusOne}/>
            ))}
        </div>
    );
}

export default ItemList;