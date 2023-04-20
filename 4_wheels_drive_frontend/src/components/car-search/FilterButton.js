import '../../styles/FilterButton.css'

export default function FilterButton(props) {
    function handleFilterClick() {
        for (let key in props.buttonStates) {
            const [active, setActive] = props.buttonStates[key];
                setActive(false);
        }
        props.handleClick();
    }

    return (
        <div className='filter-button-container'>
            <button id='button' onClick={handleFilterClick}>
                {props.name}
            </button>
        </div>
    );
}