package hu.nye.algterv.transfersystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.QAbstractAuditable;
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

   private List<TravelInfo> loadedTravelInfos = new ArrayList<>();
   
   @Autowired
   public CountryService(FlightRepository flightRepository, BusLineRepository busLineRepository, TrainLineRepository trainLineRepository, ShipLineRepository shipLineRepository, IdFinder idFinder) {
       this.flightRepository = flightRepository;
       this.busLineRepository = busLineRepository;
       this.trainLineRepository = trainLineRepository;
       this.shipLineRepository = shipLineRepository;
       this.idFinder = idFinder;
   }   

   private void loadTravelInfos() {
    loadedTravelInfos = new ArrayList<>();
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
        loadTravelInfos();
        List<TravelInfo> startTravelInfos = getStartTravelInfos(from);
        startTravelInfos.forEach(t->checkNext(t, to, from));
        List<TravelInfo> allResults = getAllTravelInfos();
        allTransports = new ArrayList<>();
        asd(from, to, allResults);
        System.out.println("\n\nasd");
    allTransports.forEach(a->{a.forEach(b->{System.out.println(b.getFromSettlement().getSettlementName() + " " + b.getToSettlement().getSettlementName() + " " + b.getTransportType());});System.out.println();});

    }

    private List<List<TravelInfo>> asd(Settlement from, Settlement to, List<TravelInfo> allResults) {
        System.out.println("start");
        List<TravelInfo> deletableInfos = new ArrayList<>();
        allResults.forEach(t->{
            if (t.getFromSettlement().getSettlementId().equals(from.getSettlementId()) && t.getToSettlement().getSettlementId().equals(to.getSettlementId())) {
                List<TravelInfo> temp = new ArrayList<>();
                temp.add(t);
                allTransports.add(temp);
                deletableInfos.add(t);
            }
        });
        deletableInfos.forEach(i->allResults.remove(i));
        List<TravelInfo> firsts = allResults.stream().filter(t->t.getFromSettlement().getSettlementId().equals(from.getSettlementId())).toList();
        List<List<TravelInfo>> otherInfos = new ArrayList<>();
        deletableInfos.clear();
        firsts.forEach(f->{
            List<TravelInfo> temp = new ArrayList<>();
            for (TravelInfo a: allResults) {
                if (a.getFromSettlement().getSettlementId().equals(
                    f.getToSettlement().getSettlementId()) && !deletableInfos.contains(a)) {
                    temp.add(a);
                    deletableInfos.add(a);
                }
            }
            otherInfos.add(temp);
            temp = new ArrayList<>();
        });
        List<TravelInfo> sortedFirsts = new ArrayList<>();
        for (TravelInfo i : firsts) {
            if (!sortedFirsts.contains(i)) {
                sortedFirsts.add(i);
            }
        }
        sortedFirsts.forEach(b->System.out.println(b.getFromSettlement().getSettlementName() + " " + b.getToSettlement().getSettlementName() + " " + b.getTransportType()));
        System.out.println("\n");
        otherInfos.forEach(a->{a.forEach(b->{System.out.println(b.getFromSettlement().getSettlementName() + " " + b.getToSettlement().getSettlementName() + " " + b.getTransportType());});System.out.println();});
        sortedFirsts.forEach(f->{
            System.out.println(f.getFromSettlement().getSettlementName() + " " + f.getToSettlement().getSettlementName() + " " + f.getTransportType());
            for (List<TravelInfo> i : otherInfos){
                if (!i.isEmpty()) {
                    if (i.get(0).getFromSettlement().getSettlementId().equals(f.getToSettlement().getSettlementId())) {
                        i.forEach(j->{
                            List<TravelInfo> temp = new ArrayList<>();
                            temp.add(f);
                            temp.add(j);
                            allTransports.add(temp);
                        });
                        
                    }
                }
            };
        });
        System.out.println("finish");
        return null;
    }

    private List<TravelInfo> getAllTravelInfos() {
        List<TravelInfo> result = new ArrayList<>();
        allTransports.forEach(t->t.forEach(result::add));
        return result;
    }

    private List<TravelInfo> tempInfos = new ArrayList<>();

    private void checkNext(TravelInfo travelInfo, Settlement to, Settlement from) {
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
                newTravelInfo.forEach(f->checkNext(f, to, from));
            }            
        }  
    }

    private List<TravelInfo> getStartTravelInfos(Settlement settlement) {
        return this.loadedTravelInfos.stream().filter(f-> {
           return f.getFromSettlement().getSettlementId().equals(settlement.getSettlementId());
        }).toList();
    }

}
