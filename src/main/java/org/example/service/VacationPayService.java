package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.VacationRequestDTO;
import org.example.dto.VacationResponseDTO;
import org.example.mapper.VacationMapper;
import org.example.model.AccountingData;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VacationPayService {
    public VacationResponseDTO createResponseDTO(VacationRequestDTO dto) {
        AccountingData data = new AccountingData();

        List<BigDecimal> monthSalaries = convertMonthSalariesToBigDecimal(dto.getMonthSalaries());
        log.debug("monthSalaries:{}", monthSalaries);
        data.setMonthSalaries(monthSalaries);

        List<BigDecimal> monthDaysWorked = convertMonthDaysWorkedToBigDecimal(dto.getMonthDaysWorked());
        log.debug("monthDaysWorked:{}", monthDaysWorked);
        data.setMonthDaysWorked(monthDaysWorked);

        List<LocalDate> vacationDates = convertVacationDatesToLocalDate(dto.getVacationDates());
        log.debug("vacationDates:{}", vacationDates);
        data.setVacationDates(vacationDates);

        BigDecimal totalSalary = calculateTotalSalaryPrevious12Month(monthSalaries);
        log.debug("totalSalary:{}", totalSalary);
        data.setTotalSalaryPrevious12Month(totalSalary);

        BigDecimal totalDays = calculateTotalWorkedDaysPrevious12Month(monthDaysWorked);
        log.debug("totalDays:{}", totalDays);
        data.setTotalWorkedDaysPrevious12Month(totalDays);

        BigDecimal averageDaySalary = calculateAverageDaySalary(totalSalary, totalDays);
        data.setAverageDaySalary(averageDaySalary);

        BigDecimal totalCompensationForVocation = averageDaySalary.multiply(BigDecimal.valueOf(vacationDates.size()));
        data.setTotalCompensationForVocation(totalCompensationForVocation);

        data.setMonthSalary(dto.getAverageMonthlySalary());

        return VacationMapper.toResponseDto(data);
    }

    private List<BigDecimal> convertMonthSalariesToBigDecimal(List<Double> monthSalaries) {
        return monthSalaries.stream().map(BigDecimal::valueOf)
                .collect(Collectors.toList());
    }

    private List<BigDecimal> convertMonthDaysWorkedToBigDecimal(List<Double> monthDaysWorked) {
        return monthDaysWorked.stream().map(BigDecimal::valueOf)
                .collect(Collectors.toList());
    }

    private List<LocalDate> convertVacationDatesToLocalDate(List<String> vacationDates) {
        return vacationDates.stream().map(LocalDate::parse).collect(Collectors.toList());
    }

    private BigDecimal calculateTotalSalaryPrevious12Month(List<BigDecimal> monthSalaries) {
        return monthSalaries.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalWorkedDaysPrevious12Month(List<BigDecimal> monthDaysWorked) {
        return monthDaysWorked.stream().reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    private BigDecimal calculateAverageDaySalary(BigDecimal totalSalaryPrevious12Month,
                                                 BigDecimal totalWorkedDaysPrevious12Month) {
        if (totalWorkedDaysPrevious12Month == null || totalWorkedDaysPrevious12Month.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Wrong input");
        }
        return totalSalaryPrevious12Month.divide(totalWorkedDaysPrevious12Month, 2, RoundingMode.HALF_UP);
    }
}
