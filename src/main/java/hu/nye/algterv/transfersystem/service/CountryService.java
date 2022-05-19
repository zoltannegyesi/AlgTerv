package hu.nye.algterv.transfersystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.nye.algterv.transfersystem.model.data.CountryData;
import hu.nye.algterv.transfersystem.model.plane.Flight;
import hu.nye.algterv.transfersystem.repository.FlightRepository;

@Service
public class CountryService {
    

   private final FlightRepository flightRepository;
   
   @Autowired
   public CountryService(FlightRepository flightRepository) {
       this.flightRepository = flightRepository;
   }

   public Integer getStartFlightId(String settlementName) {
       return this.flightRepository.findAll().stream().filter(f->f.getAirportId1().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getAirportId1().getAirportId();
   }

   public Integer getFinishFlightId(String settlementName) {
    return this.flightRepository.findAll().stream().filter(f->f.getAirportId2().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getAirportId2().getAirportId();
}



    public CountryData findRoute(Integer from, Integer to) {
        checkFlights(from, to);
        List<Flight> result = allFlights;
        allFlights = new ArrayList<>();
        Flight fromFlight = result.get(0);
        int transferCount = result.size()-1;
        Flight toFlight = result.get(transferCount);
        int time = result.stream().mapToInt(Flight::getTravelTime).sum();
        double distance = result.stream().mapToDouble(Flight::getTravelDistance).sum();
        return new CountryData(fromFlight, toFlight, transferCount, time, distance);
    }

    private void checkFlights(Integer from, Integer to) {
        List<Flight> flights = this.flightRepository.findAll().stream().filter(f->f.getAirportId1().getAirportId().equals(from)).toList();
        for (Flight f : flights) {
            checkFlight(f, to);
        }
    }

    private List<Flight> findFlightById1(Integer id) {
        return this.flightRepository.findAll().stream().filter(f->f.getAirportId1().getAirportId().equals(id)).toList();
    }

    private static List<Flight> allFlights = new ArrayList<>();
    private static List<Flight> tempAllFlights = new ArrayList<>();

    private void checkFlight(Flight flight, Integer to) {
        tempAllFlights.add(flight);
        if (flight.getAirportId2().getAirportId().equals(to)) {
            tempAllFlights.forEach(allFlights::add);
            return;
        } else {
            List<Flight> flights =  findFlightById1(flight.getAirportId2().getAirportId());
            if (flights.isEmpty()) {
                tempAllFlights = new ArrayList<>();
                return;
            } else {
                flights.forEach(f->checkFlight(f, to));
            }
           
        }
    }


}
