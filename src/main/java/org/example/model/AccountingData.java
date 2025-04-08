package org.example.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class AccountingData {
    private BigDecimal totalSalaryPrevious12Month;
    private BigDecimal totalWorkedDaysPrevious12Month;
    private BigDecimal averageDaySalary;
    private BigDecimal monthSalary;
    private List<LocalDate> vacationDates;
    private List<BigDecimal> monthSalaries;
    private List<BigDecimal> monthDaysWorked;
    private BigDecimal totalCompensationForVocation;
}
