package com.redis.caching.city.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class City {

    private String zip;
    private String city;
    private String stateName;
    private int temperature;

}
