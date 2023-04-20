import '../../styles/Dropdown.css'
import React, { useState } from 'react';

export default function Dropdown(props) {
    const [lastSelectedOption, setLastSelectedOption] = useState(null);

    function activateOptions() {
        for (let key in props.buttonStates) {
            const [active, setActive] = props.buttonStates[key];
            if (key === props.name) {
                setActive(!active);
            } else {
                setActive(false);
            }
        }
    }

    function setOptionSelected(optionName) {
        if (props.selected === optionName) {
            props.setSelected(null);
            setLastSelectedOption(null);
            console.log('deselected ' + optionName);
            return;
        }
        props.setSelected(optionName);
        setLastSelectedOption(optionName);
        console.log('selected ' + optionName);
    }

    return (
        <div className="dropdown">
            <div className="dropdown-button" onClick={activateOptions}>{props.name}<span className="arrow">▼</span></div>
            {props.buttonStates[props.name][0] && (<div className="dropdown-options">
                    {
                        props.options.map((option) => (
                            <div key={option.name} className={option.name === lastSelectedOption ? "dropdown-item dropdown-item-selected" : "dropdown-item"} 
                                 onClick={() => setOptionSelected(option.name)}>
                                {option.name}
                            </div>
                        ))
                    }
                </div>)
            }
        </div>
    )
}