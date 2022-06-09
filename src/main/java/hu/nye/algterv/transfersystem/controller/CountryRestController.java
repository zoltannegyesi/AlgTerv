package hu.nye.algterv.transfersystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.nye.algterv.transfersystem.service.CountryService;

@RestController
@RequestMapping(value = "/api/v1/routes")
public class CountryRestController {
    
    private final CountryService service;

    @Autowired
    public CountryRestController(CountryService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public List<Object> findByAirportId(@PathVariable("id") Integer id, @RequestParam("to") Integer to) {
        //return this.service.getRoute(id, to);
        return null;
    }
}
