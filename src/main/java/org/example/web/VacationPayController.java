package org.example.web;

import org.example.model.VacationResult;
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
        VacationResult result = vacationPayService.calculate(allParams);
        model.addAttribute("avgDailySalary", result.getAverageDailySalary());
        model.addAttribute("totalVacationPay", result.getTotalVacationPay());
        model.addAttribute("vacationDates", result.getVacationDates());
        return "calculatedVocation";
    }
}