package hu.nye.algterv.transfersystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.nye.algterv.transfersystem.model.TravelInfo;
import hu.nye.algterv.transfersystem.model.bus.BusLine;
import hu.nye.algterv.transfersystem.model.data.CountryData;
import hu.nye.algterv.transfersystem.model.data.SearchOptions;
import hu.nye.algterv.transfersystem.model.data.TransferIds;
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


    private final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

   private final FlightRepository flightRepository;

   private final BusLineRepository busLineRepository;

   private final TrainLineRepository trainLineRepository;

   private final ShipLineRepository shipLineRepository;

   private final IdFinder idFinder;

   private List<List<TravelInfo>> allTransports = new ArrayList<>(); 

   private List<Flight> flights = new ArrayList<>();
   private List<BusLine> busLines = new ArrayList<>();
   private List<TrainLine> trainLines = new ArrayList<>();
   private List<ShipLine> shipLines = new ArrayList<>();
   
   @Autowired
   public CountryService(FlightRepository flightRepository, BusLineRepository busLineRepository, TrainLineRepository trainLineRepository, ShipLineRepository shipLineRepository, IdFinder idFinder) {
       this.flightRepository = flightRepository;
       this.busLineRepository = busLineRepository;
       this.trainLineRepository = trainLineRepository;
       this.shipLineRepository = shipLineRepository;
       this.idFinder = idFinder;
       this.flights = flightRepository.findAll();
       busLines = busLineRepository.findAll();
       trainLines = trainLineRepository.findAll();
       shipLines = shipLineRepository.findAll();
   }   

   public TransferIds getStartId(String settlementName, SearchOptions searchOptions) {
       return this.idFinder.getStartId(settlementName, searchOptions);
   }

    public TransferIds getFinishId(String settlementName, SearchOptions searchOptions) {
        return this.idFinder.getFinishId(settlementName, searchOptions);
    }

    public void getRoute(TransferIds from, TransferIds to) {
        List<Flight> startFlights = getStartFlights(from.getAirportId());
        startFlights.forEach(f->checkNext(f, to.getAirportId()));
        List<TrainLine> startTrainLines = getStartTrainLines(from.getTrainStationId());
        startTrainLines.forEach(f->checkNext(f, to.getTrainStationId()));

        List<BusLine> startBusLines = getStartBusLines(from.getBusStationId());
        startBusLines.forEach(f->checkNext(f, to.getBusStationId()));

        List<ShipLine> startShipLines = getStartShipLines(from.getShipStationId());
        startShipLines.forEach(f->checkNext(f, to.getShipStationId()));
        allTransports.forEach(a->{a.forEach(b->{System.out.println(b.getFromSettlement().getSettlementName() + " " + b.getToSettlement().getSettlementName() + " " + b.getTransportType());});});
    }

    private List<TravelInfo> tempInfos = new ArrayList<>();

    private void checkNext(Flight flight, Integer to) {
        tempInfos.add(new TravelInfo(flight));
        if (flight.getAirportId2().getAirportId().equals(to)) {
            //successfully found
            System.out.println("Successfully found airplane route");
            List<TravelInfo> temp = new ArrayList<>();
            tempInfos.forEach(temp::add);
            tempInfos = new ArrayList<>();
            allTransports.add(temp);
            return;
        } else {
            List<Flight> newFlight = getStartFlights(flight.getAirportId2().getAirportId());
            if (newFlight.isEmpty()) {
            } else {
                newFlight.forEach(f->checkNext(f, to));
            }
            
           // checkShip(flight.getAirportId2().getSettlementId().getSettlementName(), to);
            
        }  
    }

    private void checkShip(String settlementName, Integer to) {
        try {
            Integer shipLineId = idFinder.getStartShipLineId(settlementName);
            List<ShipLine> newShipLines = getStartShipLines(shipLineId);
            newShipLines.forEach(f->checkNext(f, to));
        } catch (NoSuchElementException e) {
            return;
        }
    }

    private void checkTrain(String settlementName, Integer to) {
        try {
            Integer trainLineId = idFinder.getStartTrainLineId(settlementName);
            List<TrainLine> newTrainLines = getStartTrainLines(trainLineId);
            newTrainLines.forEach(f->checkNext(f, to));
        } catch (NoSuchElementException e) {
            return;
        }
    }

    private void checkNext(TrainLine trainLine, Integer to) {
        if (trainLine.getTrainStationId2().getTrainStationId().equals(to)) {
            //successfully found
            System.out.println("Successfully found train route");
        } else {
            List<TrainLine> newTrainLine = getStartTrainLines(trainLine.getTrainStationId2().getTrainStationId());
            newTrainLine.forEach(f->checkNext(f, to));
        }  
    }

    private void checkNext(BusLine busLine, Integer to) {
        if (busLine.getBusStationId2().getBusStationId().equals(to)) {
            //successfully found
            System.out.println("Successfully found Bus route");
        } else {
            List<BusLine> newBusLine = getStartBusLines(busLine.getBusStationId2().getBusStationId());
            newBusLine.forEach(f->checkNext(f, to));
        }  
    }

    private void checkNext(ShipLine shipLine, Integer to) {
        if (shipLine.getShipStationId2().getShipStationId().equals(to)) {
            //successfully found
            System.out.println("Successfully found Ship route");
        } else {
            List<ShipLine> newShipLine = getStartShipLines(shipLine.getShipStationId2().getShipStationId());
            newShipLine.forEach(f->checkNext(f, to));
        }  
    }
    
    private List<Flight> getStartFlights(Integer id) {
        return this.flights.stream().filter(f->f.getAirportId1().getAirportId().equals(id)).toList();
    }

    private List<BusLine> getStartBusLines(Integer id) {
        return this.busLines.stream().filter(f->f.getBusStationId1().getBusStationId().equals(id)).toList();
    }

    private List<TrainLine> getStartTrainLines(Integer id) {
        return this.trainLines.stream().filter(f->f.getTrainStationId1().getTrainStationId().equals(id)).toList();
    }

    private List<ShipLine> getStartShipLines(Integer id) {
        return this.shipLines.stream().filter(f->f.getShipStationId1().getShipStationId().equals(id)).toList();
    }

}
