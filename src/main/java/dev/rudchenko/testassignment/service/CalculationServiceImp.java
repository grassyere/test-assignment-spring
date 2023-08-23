package dev.rudchenko.testassignment.service;

import dev.rudchenko.testassignment.service.dto.ResultDTO;
import dev.rudchenko.testassignment.service.dto.TicketsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalculationServiceImp implements CalculationService {

    @Value("${app.target.origin}")
    private String TARGET_ORIGIN;
    @Value("${app.target.destination}")
    private String TARGET_DESTINATION;

    @Override
    public ResultDTO calculateResultDTO(TicketsDTO ticketsDTO) {
        var resultDTO = new ResultDTO();
        resultDTO.setMinFlightTimes(calculateMinFlightTimes(ticketsDTO));
        resultDTO.setDifference(calculatePriceDifference(ticketsDTO));
        return resultDTO;
    }

    private static Map<String, LocalTime> calculateMinFlightTimes(TicketsDTO ticketsDTO) {
        Map<String, LocalTime> minFlightTimes = new HashMap<>();
        ticketsDTO.getTickets().forEach(it -> calculateMinFlightTimeForTicket(it, minFlightTimes));
        return minFlightTimes;
    }

    private static void calculateMinFlightTimeForTicket(TicketsDTO.FlyDataDTO it,
                                                        Map<String, LocalTime> minFlightTimes) {
        String carrier = it.getCarrier();
        LocalTime departureTime = it.getDepartureTime();
        LocalTime arrivalTime = it.getArrivalTime();

        long flightDurationSeconds = arrivalTime.toSecondOfDay() - departureTime.toSecondOfDay();

        if (minFlightTimes.containsKey(carrier)) {
            LocalTime currentMinTime = minFlightTimes.get(carrier);
            if (flightDurationSeconds < currentMinTime.toSecondOfDay()) {
                minFlightTimes.put(carrier, LocalTime.ofSecondOfDay(flightDurationSeconds));
            }
        } else {
            minFlightTimes.put(carrier, LocalTime.ofSecondOfDay(flightDurationSeconds));
        }
    }

    private BigDecimal calculatePriceDifference(TicketsDTO ticketsDTO) {
        List<BigDecimal> prices = ticketsDTO.getTickets()
                .stream()
                .filter(it -> TARGET_ORIGIN.equals(it.getOrigin()) && TARGET_DESTINATION.equals(it.getDestination()))
                .map(TicketsDTO.FlyDataDTO::getPrice)
                .collect(Collectors.toList());

        return calculateAverage(prices).subtract(calculateMedian(prices));
    }

    private static BigDecimal calculateAverage(List<BigDecimal> data) {

        if (data.isEmpty()) {
            return BigDecimal.ZERO;
        }
        double sum = 0;
        for (BigDecimal price : data) {
            sum += price.doubleValue();
        }
        return new BigDecimal(sum / data.size());
    }

    private static BigDecimal calculateMedian(List<BigDecimal> data) {
        if (data.isEmpty()) {
            return BigDecimal.ZERO;
        }
        var sortedData = new ArrayList<>(data);
        Collections.sort(sortedData);

        int middle = data.size() / 2;
        return  sortedData.size() % 2 == 0 ?
                sortedData.get(middle - 1).add(sortedData.get(middle)).divide(BigDecimal.valueOf(2), RoundingMode.UP)
                : sortedData.get(middle);
    }

}
