package dev.rudchenko.testassignment.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Map;

@Setter
@Getter
public class ResultDTO {
    private Map<String, LocalTime> minFlightTimes;
    private BigDecimal difference;
}
