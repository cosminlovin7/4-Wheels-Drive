import axios from 'axios';

export function getAllBrands() {
    let brands = [];

    axios.get(process.env.REACT_APP_GET_BRANDS_URL)
        .then(response => {
            brands = response.data.brands.map(item => {
                brands.push({'id': item.id, 'name': item.brandName});
            });
        })
        .catch(error => {
            console.log(error);
        });

    return brands;
}

export function getAllModels() {
    let models = [];

    axios.get(process.env.REACT_APP_GET_ALL_MODELS_URL)
        .then(response => {
            models = response.data.map(item => {
                models.push({'id': item.id, 'name': item.modelName, 'brandId': item.brandId, 'brandName': item.brandName});
            });
        })
        .catch(error => {
            console.log(error);
        });

    return models;
}

export function getAllGenerations() {
    let generations = [];

    axios.get(process.env.REACT_APP_GET_ALL_GENERATIONS_URL)
        .then(response => {
            generations = response.data.map(item => {
                generations.push({'id': item.id, 'name': item.generationName, 'modelId': item.modelId, 'modelName': item.modelName});
            });
        })
        .catch(error => {
            console.log(error);
        });
    
    return generations;
}

export function getAllVehicles() {
    let vehicles = [];

    axios.get(process.env.REACT_APP_GET_ALL_VEHICLES_URL)
        .then(response => {
            vehicles = response.data.carAdverts.map(carAdvert => {
                vehicles.push(carAdvert)
            })
        })
        .catch(error => {
            console.log(error);
        });

    return vehicles;
}