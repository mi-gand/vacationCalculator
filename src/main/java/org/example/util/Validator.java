package org.example.util;

import org.example.to.VacationRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static org.example.util.CalendarUtil.HOLIDAYS_WITHOUT_PAY_2025;

public class Validator {
    private static final Logger log = LoggerFactory.getLogger(Validator.class);

    public static String validateRequest(VacationRequestDTO requestDto) {
        StringBuilder responseErr = new StringBuilder();
        if (requestDto.getAverageMonthlySalary() == null) {
            responseErr.append("*Отсутствует значение оклада\n");
        }
        if (requestDto.getAverageMonthlySalary() != null && requestDto.getAverageMonthlySalary()
                .compareTo(BigDecimal.valueOf(22400)) < 0) {
            responseErr.append("*МРОТ в 2025 г. составляет 22400 руб.\n");
        }
        if (requestDto.getVacationDays() < 7) {
            responseErr.append("*Меньше чем на 7 дней нельзя уходить в отпуск\n");
        }
        if (requestDto.getVacationDates() == null) {
            responseErr.append("*Не выбраны даты отпуска\n");
        }
        if (requestDto.getVacationDates().size() != requestDto.getVacationDays()) {
            responseErr.append("*Количество выбранных дней не совпадает с введённым числом отпускных\n");
        }
        if (requestDto.getVacationDates().stream().anyMatch(HOLIDAYS_WITHOUT_PAY_2025::contains)) {
            responseErr.append("*В выбранные дни отпуска попадают праздничные дни\n");
        }
        log.info("Value of Err: {}", responseErr);
        return responseErr.length() == 0 ? null : responseErr.toString();
    }
}
