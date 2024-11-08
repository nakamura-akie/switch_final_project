import React from 'react';

function Button(props) {
    return (
        <button
            type={props.type}
            className={props.style}
            onClick={props.onClick}
        >
            {props.children}
        </button>
    );
}

export default Button;
