import './EditVehiclePage.css'

function EditVehiclePage(props) {
    return (
        <>
            {props.authToken !== null ? 
                (props.vehicleToEdit !== null ? (<div>Edit page</div>) : (<div>You must select a vehicle to be able to modify it</div>)) 
                :
                (<div>You are not authorized to see this page :(</div>)
            }
        </>
    );
}

export default EditVehiclePage;