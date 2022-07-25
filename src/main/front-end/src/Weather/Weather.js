import { useEffect, useState } from 'react';
import axios from 'axios';
import './Weather.css';

const testStartingLatitude = -48.188305;
const testStartingLongitude = -67.674405;

const testDestinationLatitude = 51.5085;
const testDestinationLongitude = -0.1257;

const serverAddress = 'http://localhost:8080';

const Weather = () => {
    const [startingLocationWeather, setStartingLocationWeather] = useState({});
    const [destinationLocationWeather, setDestinationLocationWeather] = useState({});

    const [startingLocationWeatherSymbolURL, setStartingLocationWeatherSymbolURL] = useState('');
    const [destinationLocationWeatherSymbolURL, setDestinationLocationWeatherSymbolURL] = useState('');

    useEffect(() => {
        const parseResponseData = (response) => {
            const parsedObject = {
                temp: Math.round(response['temp']),
                symbol: response['weather'][0]['main'],
                description: response['weather'][0]['description']
            }

            return parsedObject;
        };

        const getCurrentWeatherAtLocation = (latitude, longitude, weatherSetter) => {
            axios.get(serverAddress + '/currentWeather?lat=' + latitude + '&lon=' + longitude).then(response => {
                weatherSetter(parseResponseData(response.data.current));
            }).catch(error => {
                console.log('Could not fetch weather data');
                console.error(error);
            });
        }

        getCurrentWeatherAtLocation(testStartingLatitude, testStartingLongitude, setStartingLocationWeather);
        getCurrentWeatherAtLocation(testDestinationLatitude, testDestinationLongitude, setDestinationLocationWeather);
    }, []);

    useEffect(() => {
        const getCurrentWeatherSymbol = (description, urlSetter) => {
            axios.get(serverAddress + '/getWeatherSymbolURL?description=' + description).then(response => {
                urlSetter(response.data);
            }).catch(error => {
                console.log('Could not fetch current weather symbol');
                return '';
            });
        };

        getCurrentWeatherSymbol(startingLocationWeather.description, setStartingLocationWeatherSymbolURL);
        getCurrentWeatherSymbol(destinationLocationWeather.description, setDestinationLocationWeatherSymbolURL);
    }, [startingLocationWeather, destinationLocationWeather]);

    const capitalizeDescription = (description) => {
        if (description) {
            return description[0].toUpperCase() + description.substring(1);
        }
        return '';
    }


    return (<div className="weatherContainer">
        <div className="informationBlock">
            <p className="largeInformationText">Starting Location</p>
            <h2 className="temperature">{startingLocationWeather.temp}&#176;C</h2>
            <div className="imageContainer"><img src={startingLocationWeatherSymbolURL} alt="Weather" className="temperature weatherIcon" /></div>
            <p className="smallInformationText">{capitalizeDescription(startingLocationWeather.description)}</p>
        </div>
        <div className="informationBlock">
        <p className="largeInformationText">Destination Location</p>
            <h2 className="temperature">{destinationLocationWeather.temp}&#176;C</h2>
            <div className="imageContainer"><img src={destinationLocationWeatherSymbolURL} alt="Weather" className="temperature weatherIcon" /></div>
            <p className="smallInformationText">{capitalizeDescription(destinationLocationWeather.description)}</p>
        </div>
    </div>)
};

export default Weather;