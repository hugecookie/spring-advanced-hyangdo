package org.example.expert.client;

import org.example.expert.client.dto.WeatherDto;
import org.example.expert.domain.common.exception.ServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeatherClientTest {

    private WeatherClient weatherClient;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        RestTemplateBuilder builder = Mockito.mock(RestTemplateBuilder.class);
        Mockito.when(builder.build()).thenReturn(restTemplate);

        weatherClient = new WeatherClient(builder);
    }

    @Test
    void 오늘날씨_정상_조회() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));
        WeatherDto[] response = new WeatherDto[] {
                new WeatherDto(today, "sunny"),
                new WeatherDto("04-20", "rain")
        };

        Mockito.when(restTemplate.getForEntity(Mockito.any(), Mockito.eq(WeatherDto[].class)))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        String result = weatherClient.getTodayWeather();
        assertThat(result).isEqualTo("sunny");
    }

    @Test
    void 오늘날씨_없으면_예외() {
        WeatherDto[] response = new WeatherDto[] {
                new WeatherDto("04-20", "rain")
        };

        Mockito.when(restTemplate.getForEntity(Mockito.any(), Mockito.eq(WeatherDto[].class)))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        assertThrows(ServerException.class, () -> weatherClient.getTodayWeather());
    }
}
