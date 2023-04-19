import '../styles/FrontSliderButton.css'

export default function FrontSliderButton(props) {
    return (
        <button className="front-slider-button" onClick={props.handleClick}>
            {props.buttonName}
        </button>
    );
}