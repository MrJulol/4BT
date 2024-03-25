package example2;

public class WeatherReporter {
    private WeatherService weatherService;

    public WeatherReporter(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    public String generateWeatherReport() {
        String returnThis = "";
        double temperature = weatherService.getCurrentTemperature();
        if (temperature > 30) {
            return returnThis;
        } else if (temperature > 20) {
            return "Angenehmes Wetter heute.";
        } else {
            return "Es ist ziemlich kalt.";
        }
    }
}
