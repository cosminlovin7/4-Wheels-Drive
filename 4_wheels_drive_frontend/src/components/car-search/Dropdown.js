import '../../styles/Dropdown.css'
import React, { useState } from 'react';

export default function Dropdown(props) {
    function activateOptions() {
        for (let key in props.buttonStates) {
            const [active, setActive] = props.buttonStates[key];
            if (key === props.name && props.otherActive !== null) {
                setActive(!active);
            } else {
                setActive(false);
            }
        }
    }

    function setOptionSelected(option) {
        if (props.buttonSelected[props.name][0]['name'] === option['name']) {
            props.buttonSelected[props.name][1]({'id': null, 'name': null});
            props.buttonStates[props.name][1](false);

            if (props.name === 'Brand') {
                props.buttonSelected['Model'][1]({'id': null, 'name': null});
            }

            if (props.name === 'Model' || props.name === 'Brand') {
                props.buttonSelected['Generation'][1]({'id': null, 'name': null});
            }
            return;
        }
        props.buttonSelected[props.name][1](option);

        if (props.name === 'Brand') {
            props.buttonSelected['Model'][1]({'id': null, 'name': null});
        }

        if (props.name === 'Brand' || props.name === 'Model') {
            props.buttonSelected['Generation'][1]({'id': null, 'name': null});
        }

        props.buttonStates[props.name][1](false);
    }

    return (
        <div className="dropdown">
            <div className={props.otherActive['name'] !== null ? 'dropdown-button button' : 'dropdown-button dropdown-button-unavailable'} onClick={activateOptions}>{props.name}<span className="arrow">▼</span></div>
            {props.buttonStates[props.name][0] && (<div className="dropdown-options">
                    {
                        props.options.map((option) => (
                            <div key={option.id} 
                                 className={option.name === props.buttonSelected[props.name][0]['name'] ? "dropdown-item dropdown-item-selected" : "dropdown-item"} 
                                 onClick={() => setOptionSelected({'id': option.id, 'name': option.name})}>
                                {option.name}
                            </div>
                        ))
                    }
                </div>)
            }
        </div>
    )
}