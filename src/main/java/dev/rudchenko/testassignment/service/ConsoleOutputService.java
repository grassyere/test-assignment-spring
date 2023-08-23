package dev.rudchenko.testassignment.service;

import dev.rudchenko.testassignment.service.dto.ResultDTO;
import org.springframework.stereotype.Service;

@Service
public class ConsoleOutputService implements OutputService {
    @Override
    public void print(ResultDTO resultDTO) {

        System.out.printf("Minimal Flight Times: %s \n" +
                        "Difference between average price and median: %s",
                resultDTO.getMinFlightTimes().toString(),
                resultDTO.getDifference());
    }
}
