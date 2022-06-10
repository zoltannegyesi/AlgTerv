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
import hu.nye.algterv.transfersystem.model.TravelInfo;
import hu.nye.algterv.transfersystem.model.data.CityData;
import hu.nye.algterv.transfersystem.model.data.SearchOptions;
import hu.nye.algterv.transfersystem.model.region.Settlement;
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
            Settlement from = this.service.getStartId(searchingData.getDeparture());
            Settlement to = this.service.getFinishId(searchingData.getArrival());
            if (from == null || to == null) {
                return "country/routes";
            }
                List<CityData> result = this.service.getRoute(from, to, searchOptions);
                model.addAttribute("cityData", result);
        }
        return "country/routes";
    }

    @GetMapping(value = "/list")
    public String findAll(Model model, @ModelAttribute("cityData") CityData cityData) {
        return "country/routes";
    }

    @GetMapping(value = "/route")
    public String getRoute(Model model, @ModelAttribute CityData cityData) {
        if (cityData == null) {
            return "country/routes";
        }
        List<TravelInfo> result = this.service.getTravelInfosById(cityData.getId());
        model.addAttribute("travelInfos", result);
        return "country/route";
    }
}
