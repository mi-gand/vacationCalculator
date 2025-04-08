package org.example.service;

import org.example.controller.VacationPayController;
import org.example.model.AccountingData;
import org.example.model.VacationRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacationPayService {
    private static final Logger log = LoggerFactory.getLogger(VacationPayService.class);

    public AccountingData createModelAccountingData(VacationRequestDTO dto) {
        AccountingData data = new AccountingData();

        List<BigDecimal> monthSalaries = convertMonthSalariesToBigDecimal(dto.getMonthSalaries());
        log.info("monthSalaries:{}", monthSalaries);
        List<BigDecimal> monthDaysWorked = convertMonthDaysWorkedToBigDecimal(dto.getMonthDaysWorked());
        log.info("monthDaysWorked:{}", monthDaysWorked);
        List<LocalDate> vacationDates = convertVacationDatesToLocalDate(dto.getVacationDates());
        log.info("vacationDates:{}", vacationDates);
        BigDecimal totalSalary = calculateTotalSalaryPrevious12Month(monthSalaries);
        log.info("totalSalary:{}", totalSalary);
        BigDecimal totalDays = calculateTotalWorkedDaysPrevious12Month(monthDaysWorked);
        log.info("totalDays:{}", totalDays);
        BigDecimal averageDaySalary = calculateAverageDaySalary(totalSalary, totalDays);
        BigDecimal totalCompensationForVocation = averageDaySalary.multiply(BigDecimal.valueOf(vacationDates.size()));

        data.setTotalSalaryPrevious12Month(totalSalary);
        data.setTotalWorkedDaysPrevious12Month(totalDays);
        data.setAverageDaySalary(averageDaySalary);
        data.setMonthSalary(dto.getAverageMonthlySalary());
        data.setVacationDates(vacationDates);
        data.setMonthSalaries(monthSalaries);
        data.setMonthDaysWorked(monthDaysWorked);
        data.setTotalCompensationForVocation(totalCompensationForVocation);

        return data;
    }

    public List<BigDecimal> convertMonthSalariesToBigDecimal(List<Double> monthSalaries) {
        return monthSalaries.stream().map(BigDecimal::valueOf)
                .collect(Collectors.toList());
    }

    public List<BigDecimal> convertMonthDaysWorkedToBigDecimal(List<Double> monthDaysWorked) {
        return monthDaysWorked.stream().map(BigDecimal::valueOf)
                .collect(Collectors.toList());
    }

    public List<LocalDate> convertVacationDatesToLocalDate(List<String> vacationDates) {
        return vacationDates.stream().map(LocalDate::parse).collect(Collectors.toList());
    }

    public BigDecimal calculateTotalSalaryPrevious12Month(List<BigDecimal> monthSalaries) {
        return monthSalaries.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalWorkedDaysPrevious12Month(List<BigDecimal> monthDaysWorked) {
        return monthDaysWorked.stream().reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    public BigDecimal calculateAverageDaySalary(BigDecimal totalSalaryPrevious12Month,
                                                BigDecimal totalWorkedDaysPrevious12Month) {
        if (totalWorkedDaysPrevious12Month == null || totalWorkedDaysPrevious12Month.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Wrong input"); //todo exception and handler
        }
        return totalSalaryPrevious12Month.divide(totalWorkedDaysPrevious12Month, 2, RoundingMode.HALF_UP);
    }
}
