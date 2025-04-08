package org.example.model;

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

    public VacationResponseDTO(AccountingData data) {
        this.averageDaySalary = data.getAverageDaySalary();
        this.monthSalary = data.getMonthSalary();
        this.vacationDates = data.getVacationDates();
        this.totalCompensationForVocation = data.getTotalCompensationForVocation();
    }
}
