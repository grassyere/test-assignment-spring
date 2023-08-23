package dev.rudchenko.testassignment.service;

import dev.rudchenko.testassignment.service.dto.ResultDTO;
import dev.rudchenko.testassignment.service.dto.TicketsDTO;

public interface CalculationService {
    ResultDTO calculateResultDTO(TicketsDTO ticketsDTO);
}
