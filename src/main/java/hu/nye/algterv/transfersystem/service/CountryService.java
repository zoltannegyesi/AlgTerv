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

   private List<TravelInfo> loadedTravelInfos = new ArrayList<>();
   
   @Autowired
   public CountryService(FlightRepository flightRepository, BusLineRepository busLineRepository, TrainLineRepository trainLineRepository, ShipLineRepository shipLineRepository, IdFinder idFinder) {
       this.flightRepository = flightRepository;
       this.busLineRepository = busLineRepository;
       this.trainLineRepository = trainLineRepository;
       this.shipLineRepository = shipLineRepository;
       this.idFinder = idFinder;
       flightRepository.findAll().forEach(f->loadedTravelInfos.add(new TravelInfo(f)));
       busLineRepository.findAll().forEach(b->loadedTravelInfos.add(new TravelInfo(b)));
       trainLineRepository.findAll().forEach(t->loadedTravelInfos.add(new TravelInfo(t)));
       shipLineRepository.findAll().forEach(s->loadedTravelInfos.add(new TravelInfo(s)));

   }   

   public Settlement getStartId(String settlementName, SearchOptions searchOptions) {
       return this.idFinder.getStartSettlement(settlementName, searchOptions);
   }

    public Settlement getFinishId(String settlementName, SearchOptions searchOptions) {
        return this.idFinder.getFinishSettlement(settlementName, searchOptions);
    }

    public void getRoute(Settlement from, Settlement to) {
        List<TravelInfo> startTravelInfos = getStartTravelInfos(from);
        startTravelInfos.forEach(t->checkNext(t, to));
        allTransports.forEach(a->{a.forEach(b->{System.out.println(b.getFromSettlement().getSettlementName() + " " + b.getToSettlement().getSettlementName() + " " + b.getTransportType());});System.out.println();});
    
    }

    private List<TravelInfo> tempInfos = new ArrayList<>();


    private void checkNext(TravelInfo travelInfo, Settlement to) {
        tempInfos.add(travelInfo);
        if (travelInfo.getToSettlement().getSettlementId().equals(to.getSettlementId())) {
            LOGGER.info("Successfully found route");
            List<TravelInfo> temp = new ArrayList<>();
            tempInfos.forEach(temp::add);
            allTransports.add(temp);
            tempInfos = new ArrayList<>();
            return;
        } else {
            List<TravelInfo> newTravelInfo = getStartTravelInfos(travelInfo.getToSettlement());
            if (!newTravelInfo.isEmpty()) {
                newTravelInfo.forEach(f->checkNext(f, to));
            }            
        }  
    }

    private List<TravelInfo> getStartTravelInfos(Settlement settlement) {
        return this.loadedTravelInfos.stream().filter(f-> {
           return f.getFromSettlement().getSettlementId().equals(settlement.getSettlementId());
        }).toList();
    }
}
