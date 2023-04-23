import '../../styles/ViewCarInfo.css'

export default function ViewCarInfo(props) {
    return (
        <>
            {props.vehicle !== null && (
                <div id="car-info-container" className="view-car-info-container">
                    <div className="basic-info-container">
                        <div className="img-container">
                            {props.vehicle['photos'] !== null && (
                                <img className="img" src={props.vehicle['photos'][0]} alt='Image'/>
                            )}
                        </div>
                        <div className="container">
                            <div>Details</div>
                            <div className="info-container">
                                <div className="info-fields">
                                    <div>Brand</div>
                                    <div>Model</div>
                                    <div>Generation</div>
                                    <div>Year</div>
                                    <div>Kilometers</div>
                                    <div>Price</div>
                                    <div>Fuel Type</div>
                                    <div>Country</div>
                                    <div>Body Type</div>
                                    <div>Color</div>
                                    <div>Number of Seats</div>
                                    <div>Emission Level</div>
                                    <div>Horse Power</div>
                                    <div>Cylinder Capacity</div>
                                    <div>Transmission</div>
                                    <div>Drivetrain</div>
                                </div>
                                <div className="info-content">
                                    <div>{props.vehicle['brand']}</div>
                                    <div>{props.vehicle['model']}</div>
                                    <div>{props.vehicle['generation']}</div>
                                    <div>{props.vehicle['year']}</div>
                                    <div>{props.vehicle['km']}</div>
                                    <div>{props.vehicle['price']}</div>
                                    <div>{props.vehicle['fuelType']}</div>
                                    <div>{props.vehicle['country']}</div>
                                    <div>{props.vehicle['body']['bodyType']}</div>
                                    <div>{props.vehicle['body']['colorType']}</div>
                                    <div>{props.vehicle['body']['nrOfSeats']}</div>
                                    <div>{props.vehicle['engine']['emissionLevelType']}</div>
                                    <div>{props.vehicle['engine']['horsePower']}</div>
                                    <div>{props.vehicle['engine']['cylinderCapacity']}</div>
                                    <div>{props.vehicle['engine']['transmissionType']}</div>
                                    <div>{props.vehicle['engine']['drivetrain']}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="additional-options-container">
                        <div>Options</div>
                        <div className="additional-options-flex-container">
                            <div className="column1">
                                <div>{props.vehicle['options']['hasAppleCarPlay'] === true ? '✓' : '✕'} Apple Car Play</div>
                                <div>{props.vehicle['options']['hasHeadUpDisplay'] === true ? '✓' : '✕'} Head Up Display</div>
                                <div>{props.vehicle['options']['hasAndroid'] === true ? '✓' : '✕'} Android</div>
                                <div>{props.vehicle['options']['hasBluetooth'] === true ? '✓' : '✕'} Bluetooth</div>
                                <div>{props.vehicle['options']['hasAirConditioning'] === true ? '✓' : '✕'} Air Conditioning</div>
                                <div>{props.vehicle['options']['hasDualClimatic'] === true ? '✓' : '✕'} Dual Climatic</div>
                            </div>
                            <div className="column2">
                                <div>{props.vehicle['options']['hasPanoramicRoof'] === true ? '✓' : '✕'} Panoramic Roof</div>
                                <div>{props.vehicle['options']['hasElectricDriverSeat'] === true ? '✓' : '✕'} Electric Driver Seat</div>
                                <div>{props.vehicle['options']['hasElectricPassengerSeat'] === true ? '✓' : '✕'} Electric Passenger Seat</div>
                                <div>{props.vehicle['options']['hasElectricSeats'] === true ? '✓' : '✕'} Electric Seats</div>
                                <div>{props.vehicle['options']['hasHeatedDriverSeat'] === true ? '✓' : '✕'} Heated Driver Seat</div>
                                <div>{props.vehicle['options']['hasHeatedPassengerSeat'] === true ? '✓' : '✕'} Heated Passenger Seat</div>
                            </div>
                            <div className="column3">
                                <div>{props.vehicle['options']['hasHeatedSteeringWheel'] === true ? '✓' : '✕'} Heated Steering Wheel</div>
                                <div>{props.vehicle['options']['hasCruiseControl'] === true ? '✓' : '✕'} Cruise Control</div>
                                <div>{props.vehicle['options']['hasDistanceControl'] === true ? '✓' : '✕'} Distance Control</div>
                                <div>{props.vehicle['options']['has360Cameras'] === true ? '✓' : '✕'} 360 Cameras</div>
                                <div>{props.vehicle['options']['hasParkingSensors'] === true ? '✓' : '✕'} Parking Sensors</div>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </>
    );
}