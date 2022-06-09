package hu.nye.algterv.transfersystem.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.nye.algterv.transfersystem.model.region.Settlement;
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

   public Settlement getStartSettlement(String settlementName) {
    try {
        return getStartFlightSettlement(settlementName);
    } catch (NoSuchElementException e) {
       
    }
    try {
        return getStartTrainLineSettlement(settlementName);
    } catch (NoSuchElementException e) {
       
    }
    try {
        return getStartBusLineSettlement(settlementName);
    } catch (NoSuchElementException e) {
       
    }
    try {
        return getStartShipLineSettlement(settlementName);
    } catch (NoSuchElementException e) {
      
    }
    return null;
}
 
    public Settlement getFinishSettlement(String settlementName) {
        try {
            return getFinishFlightSettlement(settlementName);
        } catch (NoSuchElementException e) {
           
        }
        try {
            return getFinishTrainLineSettlement(settlementName);
        } catch (NoSuchElementException e) {
           
        }
        try {
            return getFinishBusLineSettlement(settlementName);
        } catch (NoSuchElementException e) {
          
        }
        try {
            return getFinishShipLineSettlement(settlementName);
        } catch (NoSuchElementException e) {
          
        }
        return null; 
    }
 
     public Settlement getStartFlightSettlement(String settlementName) {
        return this.flightRepository.findAll().stream().filter(f->f.getAirportId1().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getAirportId1().getSettlementId();
     }
 
     public Settlement getFinishFlightSettlement(String settlementName) {
         return this.flightRepository.findAll().stream().filter(f->f.getAirportId2().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getAirportId2().getSettlementId();
     }
 
     public Settlement getStartBusLineSettlement(String settlementName) {
         return this.busLineRepository.findAll().stream().filter(bus->bus.getBusStationId1().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getBusStationId1().getSettlementId();
     }
 
     public Settlement getFinishBusLineSettlement(String settlementName) {
         return this.busLineRepository.findAll().stream().filter(bus->bus.getBusStationId2().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getBusStationId2().getSettlementId();
     }
 
     public Settlement getStartTrainLineSettlement(String settlementName) {
         return this.trainLineRepository.findAll().stream().filter(Train->Train.getTrainStationId1().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getTrainStationId1().getSettlementId();
     }
 
     public Settlement getFinishTrainLineSettlement(String settlementName) {
         return this.trainLineRepository.findAll().stream().filter(Train->Train.getTrainStationId2().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getTrainStationId2().getSettlementId();
     }
 
     public Settlement getStartShipLineSettlement(String settlementName) {
         return this.shipLineRepository.findAll().stream().filter(Ship->Ship.getShipStationId1().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getShipStationId1().getSettlementId();
     }
 
     public Settlement getFinishShipLineSettlement(String settlementName) {
         return this.shipLineRepository.findAll().stream().filter(Ship->Ship.getShipStationId2().getSettlementId().getSettlementName().equals(settlementName)).findFirst().orElseThrow().getShipStationId2().getSettlementId();
     }
    
}
