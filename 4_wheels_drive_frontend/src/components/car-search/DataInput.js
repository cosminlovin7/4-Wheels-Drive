import '../../styles/DataInput.css'
import React, { useState } from 'react';

export default function DataInput(params) {
    const [showDefaultText, setShowDefaultText] = useState(true);

    function handleValueChange(event) {
        if (event.target.value == '') {
            params.setValue('');
            setShowDefaultText(true);
            console.log('e null acum');
            return;
        }
        setShowDefaultText(false);
        params.setValue(event.target.value);
        console.log('new value written: ' + event.target.value);
    }

    return (
        <div className="input-container">
            <input id="input" 
                   step="any" 
                   type="number" 
                   value={params.value} 
                   onChange={handleValueChange}
                   placeholder={showDefaultText ? params.defaultText : ''}/>
        </div>
    );
}