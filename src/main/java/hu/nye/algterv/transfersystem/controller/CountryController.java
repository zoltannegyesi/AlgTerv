package hu.nye.algterv.transfersystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hu.nye.algterv.transfersystem.model.SearchingData;
import hu.nye.algterv.transfersystem.model.data.CountryData;
import hu.nye.algterv.transfersystem.model.data.SearchOptions;
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
    public String findRoutes(@ModelAttribute("searchingData") SearchingData searchingData, Model model) {
        SearchOptions searchOptions = new SearchOptions(searchingData);
        if (searchOptions.isNotEmpty()) {
            Integer fromId = this.service.getStartId(searchingData.getDeparture(), searchOptions);
            Integer toId = this.service.getFinishId(searchingData.getArrival(), searchOptions);
            if (fromId == null || toId == null) {
                System.out.println((fromId == null) + " " + (toId == null));
                return "country/routes";
            }
            List<CountryData> result = this.service.findRoute(fromId, toId);
            //még nem műküdik listával
            model.addAttribute("countryData", result);
        }
        return "country/routes";
    }

    @GetMapping(value = "/list")
    public String findAll(Model model, @ModelAttribute("countryData") CountryData countryData) {
        return "country/routes";
    }
}
