package org.example.controller;

import org.example.model.VacationResultDTO;
import org.example.service.VacationPayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class VacationPayController {

    private final VacationPayService vacationPayService;

    public VacationPayController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @GetMapping("/calculate")
    public String calculate(@RequestParam Map<String, String> allParams, Model model) {
        //todo validation data
        VacationResultDTO result = new VacationResultDTO(allParams);
        model.addAttribute("VacationResultDTO", result);
        return "calculatedVocation";
    }
}