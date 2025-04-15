package org.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VacationRequestDTO {
    private BigDecimal averageMonthlySalary;
    private int vacationDays;
    private List<Double> monthSalaries;
    private List<Double> monthDaysWorked;
    private List<String> vacationDates;
}
