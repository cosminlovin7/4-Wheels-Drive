import '../../styles/ViewCarInfo.css'

export default function ViewCarInfo(props) {
    return (
        <>
            {props.vehicle !== null && (
                <div id={props.id} className="view-car-info-container">
                    <div className="basic-info-container">
                        <div className="img-container">
                            {props.vehicle['photos'] !== null && (
                                <img className="img" src={props.vehicle['photos'][0]} alt='Image'/>
                            )}
                        </div>
                        <div className="details-container">
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
                                    <div>Body</div>
                                    <div>Color</div>
                                    <div>Number of Seats</div>
                                    <div>Emission Level</div>
                                    <div>Horse Power</div>
                                    <div>Cylinder Capacity</div>
                                    <div>Transmission</div>
                                    <div>Drivetrain</div>
                                </div>
                                <div className="info-content">
                                    <div>{props.vehicle['brand'] !== null ? props.vehicle['brand'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['model'] !== null ? props.vehicle['model'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['generation'] !== null ? props.vehicle['generation'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['year'] !== null ? props.vehicle['year'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['km'] !== null ? props.vehicle['km'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['price'] !== null ? props.vehicle['price'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['fuelType'] !== null ? props.vehicle['fuelType'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['country'] !== null ? props.vehicle['country'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['body']['bodyType'] !== null ? props.vehicle['body']['bodyType'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['body']['colorType'] !== null ? props.vehicle['body']['colorType'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['body']['nrOfSeats'] !== null ? props.vehicle['body']['nrOfSeats'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['engine']['emissionLevelType'] !== null ? props.vehicle['engine']['emissionLevelType'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['engine']['horsePower'] !== null ? props.vehicle['engine']['horsePower'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['engine']['cylinderCapacity'] !== null ? props.vehicle['engine']['cylinderCapacity'].toFixed(1) : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['engine']['transmissionType'] !== null ? props.vehicle['engine']['transmissionType'] : 'NOT SPECIFIED'}</div>
                                    <div>{props.vehicle['engine']['drivetrain'] !== null ? props.vehicle['engine']['drivetrain'] : 'NOT SPECIFIED'}</div>
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