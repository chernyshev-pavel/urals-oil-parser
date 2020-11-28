package ru.pavelch.uralsoilparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.pavelch.uralsoilparser.data.PriceMonitoringEntry;

import java.util.List;

@Controller
public class UralsOilWebController {
    private final List<PriceMonitoringEntry> oilPriceMonitoringEntries;

    @Autowired
    public UralsOilWebController(List<PriceMonitoringEntry> oilPriceMonitoringEntries) {
        this.oilPriceMonitoringEntries = oilPriceMonitoringEntries;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("entries", oilPriceMonitoringEntries);
        return "index";
    }
}
