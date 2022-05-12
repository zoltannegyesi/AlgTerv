package hu.nye.algterv.transfersystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping(value = "/get")
    public String findByAirportId(@RequestParam("from") String from, @RequestParam("to") String to) {
        Integer fromId = this.service.getStartFlightId(from);
        Integer toId = this.service.getFinishFlightId(to);
        CountryData result = this.service.findRoute(fromId, toId);
        return "country/index";
    }
}
