package hu.nye.algterv.transfersystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.nye.algterv.transfersystem.model.TravelInfo;
import hu.nye.algterv.transfersystem.model.bus.BusLine;
import hu.nye.algterv.transfersystem.model.data.CountryData;
import hu.nye.algterv.transfersystem.model.data.SearchOptions;
import hu.nye.algterv.transfersystem.model.plane.Flight;
import hu.nye.algterv.transfersystem.model.region.Settlement;
import hu.nye.algterv.transfersystem.model.ship.ShipLine;
import hu.nye.algterv.transfersystem.model.train.TrainLine;
import hu.nye.algterv.transfersystem.repository.BusLineRepository;
import hu.nye.algterv.transfersystem.repository.FlightRepository;
import hu.nye.algterv.transfersystem.repository.ShipLineRepository;
import hu.nye.algterv.transfersystem.repository.TrainLineRepository;

@Service
public class CountryService {
    

   private final FlightRepository flightRepository;

   private final BusLineRepository busLineRepository;

   private final TrainLineRepository trainLineRepository;

   private final ShipLineRepository shipLineRepository;

   private final IdFinder idFinder;
   
   @Autowired
   public CountryService(FlightRepository flightRepository, BusLineRepository busLineRepository, TrainLineRepository trainLineRepository, ShipLineRepository shipLineRepository, IdFinder idFinder) {
       this.flightRepository = flightRepository;
       this.busLineRepository = busLineRepository;
       this.trainLineRepository = trainLineRepository;
       this.shipLineRepository = shipLineRepository;
       this.idFinder = idFinder;
   }   

   public Integer getStartId(String settlementName, SearchOptions searchOptions) {
       return this.idFinder.getStartId(settlementName, searchOptions);
   }

    public Integer getFinishId(String settlementName, SearchOptions searchOptions) {
        return this.idFinder.getFinishId(settlementName, searchOptions);
    }
 
    public void checkLines(Integer from, Integer to) {
        checkFlights(from, to);
        checkBusLines(from, to);
    }

    public CountryData findRoute(Integer from, Integer to) {
        checkLines(from, to);
        List<CountryData> result = new ArrayList<>();
        List<List<TravelInfo>> infoList = allSuccessfullTravels;
        allSuccessfullTravels = new ArrayList<>();
        for (List<TravelInfo> tempList: infoList) {
            TravelInfo fromInfo = tempList.get(0);
            int transferCount = tempList.size()-1;
            TravelInfo toInfo = tempList.get(transferCount);
            int time = tempList.stream().mapToInt(TravelInfo::getTravelTime).sum();
            double distance = tempList.stream().mapToDouble(TravelInfo::getTravelDistance).sum();
            result.add(new CountryData(fromInfo, toInfo, transferCount, time, distance, tempList));
        }
        allSuccessfullTravels.forEach(a->{
            System.out.println(a.get(0).getTransportType());
            a.forEach(b->System.out.print(b.getFromSettlement().getSettlementName()+ " " + b.getToSettlement().getSettlementName()));
            System.out.println("\n" + a.size() + "\n\n");
        });
        
    }

    private void checkFlights(Integer from, Integer to) {
        List<Flight> flights = this.flightRepository.findAll().stream().filter(f->f.getAirportId1().getAirportId().equals(from)).toList();
        for (Flight f : flights) {
            checkFlight(f, to);
        }
    }

    private void checkBusLines(Integer from, Integer to) {
        List<BusLine> busLines = this.busLineRepository.findAll().stream().filter(bus->bus.getBusStationId1().getBusStationId().equals(from)).toList();
        for (BusLine bus : busLines) {
            checkBusLine(bus, to);
        }
    }

    private List<Flight> findFlightById1(Integer id) {
        return this.flightRepository.findAll().stream().filter(f->f.getAirportId1().getAirportId().equals(id)).toList();
    }

    private List<BusLine> findBusLineById1(Integer id) {
        return this.busLineRepository.findAll().stream().filter(b->b.getBusStationId1().getBusStationId().equals(id)).toList();
    }

    

    private static List<TravelInfo> allTravels = new ArrayList<>();
    private static List<TravelInfo> tempAllTravels = new ArrayList<>();
    private static List<List<TravelInfo>> allSuccessfullTravels = new ArrayList<>();

    private void checkBusLine(BusLine busLine, Integer to) {
        TravelInfo travelInfo = new TravelInfo(busLine);
        tempAllTravels.add(travelInfo);
        if (busLine.getBusStationId2().getSettlementId().getSettlementId().equals(to)) {
            tempAllTravels.forEach(allTravels::add);
            allSuccessfullTravels.add(tempAllTravels);
            tempAllTravels = new ArrayList<>();
            return;
        } else {
            List<BusLine> busLines = findBusLineById1(busLine.getBusStationId2().getBusStationId());
            //itt az összeset lekérdezni, végigmenni rajtuk
            if (busLines.isEmpty()) {
                tempAllTravels = new ArrayList<>();
                return;
            } else {
                busLines.forEach(bus->checkBusLine(bus, to));
            }
           
        }
    }

    private void checkFlight(Flight flight, Integer to) {
        TravelInfo travelInfo = new TravelInfo(flight);
        tempAllTravels.add(travelInfo);
        if (flight.getAirportId2().getSettlementId().getSettlementId().equals(to)) {
            tempAllTravels.forEach(allTravels::add);
            allSuccessfullTravels.add(tempAllTravels);
            tempAllTravels = new ArrayList<>();
            return;
        } else {
            List<Flight> flightLines = findFlightById1(flight.getAirportId2().getAirportId());
            //itt az összeset lekérdezni, végigmenni rajtuk
            if (flightLines.isEmpty()) {
                tempAllTravels = new ArrayList<>();
                return;
            } else {
                flightLines.forEach(plane->checkFlight(plane, to));
            }
           
        }
    }
/*
    private void checkFlight(Flight flight, BusLine busLine, ShipLine shipLine, TrainLine trainLine, Integer to) {
      //  TravelInfo travelInfo = 
        tempAllTravles.add(flight);

        if (flight.getAirportId2().getAirportId().equals(to)) {
            tempAllFlights.forEach(allFlights::add);
            return;
        } else if (trainLine.getTrainStationId2().getSettlementId().equals(to)) {
            tempAllTravels.forEach(allTravels::add);
            return;
        } else if (shipLine.getShipStationId2().getSettlementId().equals(to)) {
            tempAllTravels.forEach(allTravels::add);
            return;
        } else if (busLine.getBusStationId2().getSettlementId().equals(to)) {
            tempAllTravels.forEach(allTravels::add);
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
    }*/


}
