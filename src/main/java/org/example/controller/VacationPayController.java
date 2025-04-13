package org.example.controller;

import org.example.model.AccountingData;
import org.example.to.VacationRequestDTO;
import org.example.to.VacationResponseDTO;
import org.example.service.VacationPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import static org.example.util.Validator.validateRequest;

@Controller
public class VacationPayController {
    private static final Logger log = LoggerFactory.getLogger(VacationPayController.class);

    @Autowired
    VacationPayService vacationPayService;

    @GetMapping("/calculate")
    public String calculate(@ModelAttribute VacationRequestDTO dtoFromPage, Model model) {
        log.info("DTO from page: {}", dtoFromPage);
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