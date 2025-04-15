package org.example.util;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.VacationRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static org.example.util.CalendarUtil.HOLIDAYS_WITHOUT_PAY_2025;

@Slf4j
public class Validator {
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
        log.debug("Value of Err: {}", responseErr);
        return responseErr.length() == 0 ? null : responseErr.toString();
    }
}
