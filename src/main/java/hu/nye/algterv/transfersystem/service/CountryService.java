package hu.nye.algterv.transfersystem.service;

import java.util.ArrayList;
import java.util.List;

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

   public TransferIds getStartId(String settlementName, SearchOptions searchOptions) {
       return this.idFinder.getStartId(settlementName, searchOptions);
   }

    public TransferIds getFinishId(String settlementName, SearchOptions searchOptions) {
        return this.idFinder.getFinishId(settlementName, searchOptions);
    }
 
    public void checkLines(TransferIds from, TransferIds to) {
        if (from.getAirportId() != null && to.getAirportId() != null) {
            System.out.println("r");
            checkFlights(from.getAirportId(), to.getAirportId());
        }
        if (from.getTrainStationId() != null && to.getTrainStationId() != null) {
            System.out.println("t");
            checkTrainLines(from.getTrainStationId(), to.getTrainStationId());
        }
        if (from.getBusStationId() != null && to.getBusStationId() != null) {
            System.out.println("b");
            checkBusLines(from.getBusStationId(), to.getBusStationId());
        }
        if (from.getShipStationId() != null && to.getShipStationId() != null) {
            System.out.println("h");
            checkShipLines(from.getShipStationId(), to.getShipStationId());
        }
    }

    public List<CountryData> findRoute(TransferIds from, TransferIds to) {
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
        return result;
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

    private void checkTrainLines(Integer from, Integer to) {
        List<TrainLine> trainLines = this.trainLineRepository.findAll().stream().filter(train->train.getTrainStationId1().getTrainStationId().equals(from)).toList();
        for (TrainLine train : trainLines) {
            checkTrainLine(train, to);
        }
    }

    private void checkShipLines(Integer from, Integer to) {
        List<ShipLine> shipLines = this.shipLineRepository.findAll().stream().filter(ship->ship.getShipStationId1().getShipStationId().equals(from)).toList();
        for (ShipLine ship : shipLines) {
            checkShipLine(ship, to);
        }
    }

    private List<Flight> findFlightById(Integer id) {
        return this.flightRepository.findAll().stream().filter(f->f.getAirportId1().getAirportId().equals(id)).toList();
    }

    private List<BusLine> findBusLineById(Integer id) {
        return this.busLineRepository.findAll().stream().filter(b->b.getBusStationId1().getBusStationId().equals(id)).toList();
    }

    private List<TrainLine> findTrainLineById(Integer id) {
        return this.trainLineRepository.findAll().stream().filter(b->b.getTrainStationId1().getTrainStationId().equals(id)).toList();
    }

    private List<ShipLine> findShipLineById(Integer id) {
        return this.shipLineRepository.findAll().stream().filter(b->b.getShipStationId1().getSettlementId().getSettlementId().equals(id)).toList();
    }

    

    private static List<TravelInfo> allTravels = new ArrayList<>();
    private static List<TravelInfo> tempAllTravels = new ArrayList<>();
    private static List<List<TravelInfo>> allSuccessfullTravels = new ArrayList<>();


    private void callCheckLines(Integer id, Integer to) {
        List<BusLine> busLines = findBusLineById(id);
        List<Flight> flights = findFlightById(id);
        List<TrainLine> trainLines = findTrainLineById(id);
        List<ShipLine> shipLines = findShipLineById(id);
        if (busLines.isEmpty()) {
            tempAllTravels = new ArrayList<>();
            return;
        } else {
            busLines.forEach(bus->checkBusLine(bus, to));
        }
        if (flights.isEmpty()) {
            tempAllTravels = new ArrayList<>();
        } else {
            flights.forEach(plain->checkFlight(plain, to));
        }
        if (trainLines.isEmpty()) {
            tempAllTravels = new ArrayList<>();
            return;
        } else {
            trainLines.forEach(train->checkTrainLine(train, to));
        }
        if (shipLines.isEmpty()) {
            tempAllTravels = new ArrayList<>();
            return;
        } else {
            shipLines.forEach(ship->checkShipLine(ship, to));
        }
    }

    private void checkBusLine(BusLine busLine, Integer to) {
        TravelInfo travelInfo = new TravelInfo(busLine);
        tempAllTravels.add(travelInfo);
        if (busLine.getBusStationId2().getSettlementId().getSettlementId().equals(to)) {
            tempAllTravels.forEach(allTravels::add);
            allSuccessfullTravels.add(tempAllTravels);
            tempAllTravels = new ArrayList<>();
            return;
        } else {
            callCheckLines(busLine.getBusStationId2().getSettlementId().getSettlementId(), to);
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
            callCheckLines(flight.getAirportId2().getSettlementId().getSettlementId(), to);
        }
    }

    private void checkTrainLine(TrainLine trainLine, Integer to) {
        TravelInfo travelInfo = new TravelInfo(trainLine);
        tempAllTravels.add(travelInfo);
        if (trainLine.getTrainStationId2().getTrainStationId().equals(to)) {
            tempAllTravels.forEach(allTravels::add);
            allSuccessfullTravels.add(tempAllTravels);
            tempAllTravels = new ArrayList<>();
            return;
        } else {
            callCheckLines(trainLine.getTrainStationId2().getTrainStationId(), to);
        }
    }

    private void checkShipLine(ShipLine shipLine, Integer to) {
        TravelInfo travelInfo = new TravelInfo(shipLine);
        tempAllTravels.add(travelInfo);
        if (shipLine.getShipStationId2().getSettlementId().getSettlementId().equals(to)) {
            tempAllTravels.forEach(allTravels::add);
            allSuccessfullTravels.add(tempAllTravels);
            tempAllTravels = new ArrayList<>();
            return;
        } else {
            callCheckLines(shipLine.getShipStationId2().getSettlementId().getSettlementId(), to);
        }
    }
}
