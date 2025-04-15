package org.example.mapper;

import org.example.dto.VacationResponseDTO;
import org.example.model.AccountingData;

public class VacationMapper {

    public static VacationResponseDTO toResponseDto(AccountingData data) {
        VacationResponseDTO dto = new VacationResponseDTO();
        dto.setAverageDaySalary(data.getAverageDaySalary());
        dto.setMonthSalary(data.getMonthSalary());
        dto.setVacationDates(data.getVacationDates());
        dto.setTotalCompensationForVocation(data.getTotalCompensationForVocation());
        return dto;
    }
}
