package org.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class VacationResponseDTO {
    private BigDecimal averageDaySalary;
    private BigDecimal monthSalary;
    private List<LocalDate> vacationDates;
    private BigDecimal totalCompensationForVocation;
}
