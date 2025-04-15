package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.VacationRequestDTO;
import org.example.dto.VacationResponseDTO;
import org.example.model.AccountingData;
import org.example.service.VacationPayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import static org.example.util.Validator.validateRequest;
@Slf4j
@RequiredArgsConstructor
@Controller
public class VacationPayController {

    private final VacationPayService vacationPayService;

    @GetMapping("/calculate")
    public String calculate(@ModelAttribute VacationRequestDTO dtoFromPage, Model model) {
        log.debug("DTO from page: {}", dtoFromPage);
        String validationError = validateRequest(dtoFromPage);
        if (validationError != null) {
            model.addAttribute("alertMessage", validationError);
            return "afterBadValidation";
        }
        AccountingData accountingData = vacationPayService.createModelAccountingData(dtoFromPage);
        VacationResponseDTO dtoToPage = new VacationResponseDTO(accountingData);
        model.addAttribute("VacationResultDTO", dtoToPage);
        return "calculatedVocation";
    }
}