package hu.nye.algterv.transfersystem.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.nye.algterv.transfersystem.model.data.SearchOptions;
import hu.nye.algterv.transfersystem.model.data.TransferIds;
import hu.nye.algterv.transfersystem.repository.BusLineRepository;
import hu.nye.algterv.transfersystem.repository.FlightRepository;
import hu.nye.algterv.transfersystem.repository.ShipLineRepository;
import hu.nye.algterv.transfersystem.repository.TrainLineRepository;

@Service
public class IdFinder {

    private final FlightRepository flightRepository;

   private final BusLineRepository busLineRepository;

   private final TrainLineRepository trainLineRepository;

   private final ShipLineRepository shipLineRepository;
   
   @Autowired
   public IdFinder(FlightRepository flightRepository, BusLineRepository busLineRepository, TrainLineRepository trainLineRepository, ShipLineRepository shipLineRepository) {
       this.flightRepository = flightRepository;
       this.busLineRepository = busLineRepository;
       this.trainLineRepository = trainLineRepository;
       this.shipLineRepository = shipLineRepository;
   }

   public TransferIds getStartId(String settlementName, SearchOptions searchOptions) {
    TransferIds transferIds = new TransferIds();
    try {
        transferIds.setAirportId(getStartFlightId(settlementName));
    } catch (NoSuchElementException e) {
       System.out.println("Nincsen ilyen reptér");
    }
    try {
        transferIds.setTrainStationId(getStartTrainLineId(settlementName));
    } catch (NoSuchElementException e) {
       System.out.println("Nincsen ilyen vasútállomás");
    }
    try {
        transferIds.setBusStationId(getStartBusLineId(settlementName));
    } catch (NoSuchElementException e) {
       System.out.println("Nincsen ilyen buszállomás");
    }
    try {
        transferIds.setShipStationId(getStartShipLineId(settlementName));
    } catch (NoSuchElementException e) {
       System.out.println("Nincsen ilyen hajóállomás");
    }
    return transferIds;
}
 
    public TransferIds getFinishId(String settlementName, SearchOptions searchOptions) {
        TransferIds transferIds = new TransferIds();
        try {
            transferIds.setAirportId(getFinishFlightId(settlementName));
        } catch (NoSuchElementException e) {
           System.out.println("Nincsen ilyen reptér");
        }
        try {
            transferIds.setTrainStationId(getFinishTrainLineId(settlementName));
        } catch (NoSuchElementException e) {
           System.out.println("Nincsen ilyen vasútállomás");
        }
        try {
            transferIds.setBusStationId(getFinishBusLineId(settlementName));
        } catch (NoSuchElementException e) {
           System.out.println("Nincsen ilyen buszállomás");
        }
        try {
            transferIds.setShipStationId(getFinishShipLineId(settlementName));
        } catch (NoSuchElementException e) {
           System.out.println("Nincsen ilyen hajóállomás");
        }
        return transferIds; 
    }
 
     private Integer getStartFlightId(String settlementName) {
        return this.flightRepository.findAll().stream().filter(f->f.getAirportId1().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getAirportId1().getAirportId();
     }
 
     private Integer getFinishFlightId(String settlementName) {
         return this.flightRepository.findAll().stream().filter(f->f.getAirportId2().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getAirportId2().getAirportId();
     }
 
     private Integer getStartBusLineId(String settlementName) {
         return this.busLineRepository.findAll().stream().filter(bus->bus.getBusStationId1().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getBusStationId1().getBusStationId();
     }
 
     private Integer getFinishBusLineId(String settlementName) {
         return this.busLineRepository.findAll().stream().filter(bus->bus.getBusStationId2().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getBusStationId2().getBusStationId();
     }
 
     private Integer getStartTrainLineId(String settlementName) {
         return this.trainLineRepository.findAll().stream().filter(Train->Train.getTrainStationId1().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getTrainStationId1().getTrainStationId();
     }
 
     private Integer getFinishTrainLineId(String settlementName) {
         return this.trainLineRepository.findAll().stream().filter(Train->Train.getTrainStationId2().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getTrainStationId2().getTrainStationId();
     }
 
     private Integer getStartShipLineId(String settlementName) {
         return this.shipLineRepository.findAll().stream().filter(Ship->Ship.getShipStationId1().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getShipStationId1().getShipStationId();
     }
 
     private Integer getFinishShipLineId(String settlementName) {
         return this.shipLineRepository.findAll().stream().filter(Ship->Ship.getShipStationId2().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getShipStationId2().getShipStationId();
     }
    
}
