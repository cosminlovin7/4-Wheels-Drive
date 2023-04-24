import './InputForm.css'

function InputForm(props) {
    function handleValueChange(event) {
        props.setValue(event.target.value);
    }

    return (
        <input className='input' type={props.type} placeholder={props.placeholder} value={props.value} onChange={handleValueChange}/>
    );
}

export default InputForm;