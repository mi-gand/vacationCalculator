package org.example.service;

import org.example.model.VacationResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VacationPayService {

    public VacationResult calculate(Map<String, String> allParams) {
        List<BigDecimal> salaries = extractBigDecimals(allParams, "salary_");
        List<BigDecimal> days = extractBigDecimals(allParams, "days_");

        BigDecimal totalSalary = salaries.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalDays = days.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalDays.compareTo(BigDecimal.ZERO) == 0) totalDays = BigDecimal.ONE;

        BigDecimal avgDaily = totalSalary.divide(totalDays, 2, RoundingMode.HALF_UP);

        List<LocalDate> vacationDates = parseVacationDates(allParams);

        BigDecimal total = avgDaily.multiply(BigDecimal.valueOf(vacationDates.size())).setScale(2, RoundingMode.HALF_UP);

        return new VacationResult(avgDaily, total, vacationDates);
    }

    private List<BigDecimal> extractBigDecimals(Map<String, String> params, String prefix) {
        List<BigDecimal> values = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            try {
                values.add(new BigDecimal(params.getOrDefault(prefix + i, "0")));
            } catch (NumberFormatException e) {
                values.add(BigDecimal.ZERO);
            }
        }
        return values;
    }

    private List<LocalDate> parseVacationDates(Map<String, String> params) {
        String raw = params.get("vacationDates");
        if (raw == null || raw.isBlank()) return Collections.emptyList();

        return Arrays.stream(raw.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    try {
                        return LocalDate.parse(s);
                    } catch (DateTimeParseException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
