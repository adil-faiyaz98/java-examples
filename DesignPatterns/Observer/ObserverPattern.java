/**
 * Observer Design Pattern
 * 
 * Intent: Define a one-to-many dependency between objects so that when one object changes state,
 * all its dependents are notified and updated automatically.
 * 
 * This example demonstrates a weather station (subject) that notifies multiple displays (observers)
 * when weather data changes. It shows both push and pull models of the Observer pattern.
 */
package design.patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class ObserverPattern {
    
    public static void main(String[] args) {
        // Create the WeatherStation (subject)
        WeatherStation weatherStation = new WeatherStation();
        
        // Create different displays (observers)
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherStation);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherStation);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherStation);
        
        // Simulate weather changes
        System.out.println("Weather station initial measurements:");
        weatherStation.setMeasurements(80, 65, 30.4f);
        
        System.out.println("\nWeather station updated measurements:");
        weatherStation.setMeasurements(82, 70, 29.2f);
        
        System.out.println("\nRemoving the current conditions display:");
        weatherStation.removeObserver(currentDisplay);
        
        System.out.println("\nWeather station final measurements:");
        weatherStation.setMeasurements(78, 90, 29.2f);
        
        // Pull model example
        System.out.println("\n--- PULL MODEL EXAMPLE ---");
        
        // Create the WeatherData (subject) for pull model
        WeatherData weatherData = new WeatherData();
        
        // Create observers for pull model
        TemperatureDisplay temperatureDisplay = new TemperatureDisplay(weatherData);
        HumidityDisplay humidityDisplay = new HumidityDisplay(weatherData);
        
        // Simulate weather changes for pull model
        System.out.println("\nWeather data initial update:");
        weatherData.setMeasurements(75, 60, 30.1f);
        
        System.out.println("\nWeather data second update:");
        weatherData.setMeasurements(81, 55, 30.3f);
    }
}

/**
 * PUSH MODEL IMPLEMENTATION
 * 
 * The Subject interface for the Push model
 */
interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

/**
 * The Observer interface for the Push model
 */
interface Observer {
    void update(float temperature, float humidity, float pressure);
}

/**
 * The Display interface for all display elements
 */
interface DisplayElement {
    void display();
}

/**
 * Concrete Subject: WeatherStation
 */
class WeatherStation implements Subject {
    private final List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    
    public WeatherStation() {
        observers = new ArrayList<>();
    }
    
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }
    
    /**
     * Sets new measurements and notifies all observers
     */
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
    
    /**
     * Called when measurements change
     */
    private void measurementsChanged() {
        notifyObservers();
    }
}

/**
 * Concrete Observer: CurrentConditionsDisplay
 */
class CurrentConditionsDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private final Subject weatherStation;
    
    public CurrentConditionsDisplay(Subject weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }
    
    @Override
    public void display() {
        System.out.println("Current conditions: " + temperature + "F degrees and " + humidity + "% humidity");
    }
}

/**
 * Concrete Observer: StatisticsDisplay
 */
class StatisticsDisplay implements Observer, DisplayElement {
    private float maxTemp = 0.0f;
    private float minTemp = 200;
    private float tempSum = 0.0f;
    private int numReadings = 0;
    private final Subject weatherStation;
    
    public StatisticsDisplay(Subject weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        tempSum += temperature;
        numReadings++;
        
        if (temperature > maxTemp) {
            maxTemp = temperature;
        }
        
        if (temperature < minTemp) {
            minTemp = temperature;
        }
        
        display();
    }
    
    @Override
    public void display() {
        System.out.println("Avg/Max/Min temperature = " + (tempSum / numReadings) + "/" + maxTemp + "/" + minTemp);
    }
}

/**
 * Concrete Observer: ForecastDisplay
 */
class ForecastDisplay implements Observer, DisplayElement {
    private float currentPressure = 29.92f;
    private float lastPressure;
    private final Subject weatherStation;
    
    public ForecastDisplay(Subject weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        lastPressure = currentPressure;
        currentPressure = pressure;
        display();
    }
    
    @Override
    public void display() {
        System.out.print("Forecast: ");
        if (currentPressure > lastPressure) {
            System.out.println("Improving weather on the way!");
        } else if (currentPressure == lastPressure) {
            System.out.println("More of the same");
        } else {
            System.out.println("Watch out for cooler, rainy weather");
        }
    }
}

/**
 * PULL MODEL IMPLEMENTATION
 * 
 * The Subject interface for the Pull model
 */
interface PullSubject {
    void registerObserver(PullObserver observer);
    void removeObserver(PullObserver observer);
    void notifyObservers();
}

/**
 * The Observer interface for the Pull model
 */
interface PullObserver {
    void update();
}

/**
 * Concrete Subject: WeatherData for Pull model
 */
class WeatherData implements PullSubject {
    private final List<PullObserver> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    
    public WeatherData() {
        observers = new ArrayList<>();
    }
    
    @Override
    public void registerObserver(PullObserver observer) {
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(PullObserver observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (PullObserver observer : observers) {
            observer.update();
        }
    }
    
    /**
     * Sets new measurements and notifies all observers
     */
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
    
    /**
     * Called when measurements change
     */
    private void measurementsChanged() {
        notifyObservers();
    }
    
    // Getter methods for observers to pull data
    public float getTemperature() {
        return temperature;
    }
    
    public float getHumidity() {
        return humidity;
    }
    
    public float getPressure() {
        return pressure;
    }
}

/**
 * Concrete Observer: TemperatureDisplay (Pull model)
 */
class TemperatureDisplay implements PullObserver, DisplayElement {
    private final WeatherData weatherData;
    
    public TemperatureDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }
    
    @Override
    public void update() {
        // Pull the data we need from the subject
        display();
    }
    
    @Override
    public void display() {
        System.out.println("Temperature Display: " + weatherData.getTemperature() + "F degrees");
    }
}

/**
 * Concrete Observer: HumidityDisplay (Pull model)
 */
class HumidityDisplay implements PullObserver, DisplayElement {
    private final WeatherData weatherData;
    
    public HumidityDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }
    
    @Override
    public void update() {
        // Pull the data we need from the subject
        display();
    }
    
    @Override
    public void display() {
        System.out.println("Humidity Display: " + weatherData.getHumidity() + "% humidity");
    }
}
