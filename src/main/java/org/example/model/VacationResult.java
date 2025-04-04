package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VacationResult {
    private final BigDecimal averageDailySalary;
    private final BigDecimal totalVacationPay;
    private final List<LocalDate> vacationDates;

    public VacationResult(BigDecimal averageDailySalary, BigDecimal totalVacationPay, List<LocalDate> vacationDates) {
        this.averageDailySalary = averageDailySalary;
        this.totalVacationPay = totalVacationPay;
        this.vacationDates = vacationDates;
    }

    public BigDecimal getAverageDailySalary() {
        return averageDailySalary;
    }

    public BigDecimal getTotalVacationPay() {
        return totalVacationPay;
    }

    public List<LocalDate> getVacationDates() {
        return vacationDates;
    }
}
