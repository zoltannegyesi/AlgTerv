package hu.nye.algterv.transfersystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hu.nye.algterv.transfersystem.model.SearchingData;
import hu.nye.algterv.transfersystem.model.data.CountryData;
import hu.nye.algterv.transfersystem.service.CountryService;

@Controller
@RequestMapping(value = "/routes")
public class CountryController {
    
    private final CountryService service;

    @Autowired
    public CountryController(CountryService service) {
        this.service = service;
    }

    @GetMapping
    public String getCountry() {
        return "country/index";
    }

    @PostMapping(value = "/list")
    public String findByAirportId(@ModelAttribute("searchinData") SearchingData searchingData, Model model) {
        Integer fromId = this.service.getStartFlightId(searchingData.getDeparture());
        Integer toId = this.service.getFinishFlightId(searchingData.getArrival());
        CountryData result = this.service.findRoute(fromId, toId);
        model.addAttribute("countryData", result);
        return "country/routes";
    }

    @GetMapping(value = "/list")
    public String findAll(Model model, @ModelAttribute("countryData") CountryData countryData) {

        return "country/routes";
    }
}
