package org.example.expert.client.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherDtoTest {

    @Test
    void getter_테스트() {
        WeatherDto dto = new WeatherDto("04-19", "sunny");

        assertThat(dto.getDate()).isEqualTo("04-19");
        assertThat(dto.getWeather()).isEqualTo("sunny");
    }
}
