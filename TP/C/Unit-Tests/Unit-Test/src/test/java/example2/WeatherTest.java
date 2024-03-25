package example2;

import org.junit.jupiter.api.*;

import static org.mockito.Mockito.*;

public class WeatherTest {
    @Test
    @DisplayName("Test Weather Stuff")
    public void generateWeatherReport(){
        WeatherService we = mock(WeatherService.class);
        when(we.getCurrentTemperature()).thenReturn(22.5);
        WeatherReporter re = new WeatherReporter(we);
        String res = re.generateWeatherReport();
        Assertions.assertEquals("Angenehmes Wetter heute.", res);
        verify(we).getCurrentTemperature();

    }

}
