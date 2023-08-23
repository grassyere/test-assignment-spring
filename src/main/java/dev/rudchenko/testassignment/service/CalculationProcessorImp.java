package dev.rudchenko.testassignment.service;

import dev.rudchenko.testassignment.service.dto.ResultDTO;
import dev.rudchenko.testassignment.service.dto.TicketsDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Getter
@Slf4j
@Component
public class CalculationProcessorImp implements CalculationProcessor {

    private final ParserService parserService;
    private final CalculationService calculationService;
    private final OutputService consoleOutputService;

    public CalculationProcessorImp(ParserService parserService,
                                   CalculationService calculationService,
                                   OutputService consoleOutputService) {
        this.parserService = parserService;
        this.calculationService = calculationService;
        this.consoleOutputService = consoleOutputService;
    }

    @Override
    public void processTask(String path) {
        TicketsDTO ticketsDTO = parserService.parseFromJson(path);
        ResultDTO resultDTO = calculationService.calculateResultDTO(ticketsDTO);
        consoleOutputService.print(resultDTO);
    }
}
