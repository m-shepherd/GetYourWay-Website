import {useEffect, useState} from 'react';
import axios from 'axios';
import './Weather.module.css';

const testLatitude = -48.188305;
const testLongitude = -67.674405;

const serverAddress = 'http://localhost:8080';

const Weather = () => {
    const [currentWeather, setCurrentWeather] = useState({});
    const [weatherSymbolURL, setWeatherSymbolURL] = useState('');

    useEffect(() => {
        const parseResponseData = (response) => {
            return {
                temp: Math.round(response['temp']),
                symbol: response['weather'][0]['main'],
                description: response['weather'][0]['description']
            };
        };

        axios.get(serverAddress + '/currentWeather?lat=' + testLatitude + '&lon=' + testLongitude).then(response => {
            setCurrentWeather(parseResponseData(response.data.current));
        }).catch(error => {
            console.log('Could not fetch weather data');
            console.error(error);
        });
    }, []);

    useEffect(() => {
        const requestWeatherSymbol = () => {
            axios.get(serverAddress + '/getWeatherSymbolURL?description=' + currentWeather.description).then(response => {
                setWeatherSymbolURL(response.data);
            }).catch(error => {
                console.log('Could not fetch current weather symbol');
            });
        };

        requestWeatherSymbol();
    }, [currentWeather]);

    const capitalizeDescription = (description) => {
        return description[0].toUpperCase() + description.substring(1);
    }


    return (<div className="weatherContainer">
        <div className="informationBlock">
            <p className="largeInformationText">Location</p>
            <h2 className="temperature">{currentWeather.temp}&#176;C</h2>
        </div>
        <div className="informationBlock">
            <img src={weatherSymbolURL} alt="Weather" className="temperature" />
            <p className="smallInformationText">{capitalizeDescription(currentWeather.description)}</p>
        </div>
    </div>)
};

export default Weather;