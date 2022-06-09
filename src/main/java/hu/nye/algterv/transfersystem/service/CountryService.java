package hu.nye.algterv.transfersystem.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.nye.algterv.transfersystem.model.TravelInfo;
import hu.nye.algterv.transfersystem.model.data.CountryData;
import hu.nye.algterv.transfersystem.model.data.SearchOptions;
import hu.nye.algterv.transfersystem.model.region.Settlement;
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

   private void loadTravelInfos(SearchOptions searchOptions) {
    loadedTravelInfos = new ArrayList<>();
    allTransports = new ArrayList<>();
    if (searchOptions.getIsBus()) {
        busLineRepository.findAll().forEach(b->loadedTravelInfos.add(new TravelInfo(b)));
    }
    if (searchOptions.getIsPlane()) {
        flightRepository.findAll().forEach(f->loadedTravelInfos.add(new TravelInfo(f)));
    }
    if (searchOptions.getIsTrain()) {
        trainLineRepository.findAll().forEach(t->loadedTravelInfos.add(new TravelInfo(t)));
    }
    if (searchOptions.getIsShip()) {
        shipLineRepository.findAll().forEach(s->loadedTravelInfos.add(new TravelInfo(s)));
    }
   }

   public Settlement getStartId(String settlementName) {
       return this.idFinder.getStartSettlement(settlementName);
   }

    public Settlement getFinishId(String settlementName) {
        return this.idFinder.getFinishSettlement(settlementName);
    }

    private Double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
    
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public List<CountryData> getRoute(Settlement from, Settlement to, SearchOptions searchOptions) {
        loadTravelInfos(searchOptions);
        List<TravelInfo> startTravelInfos = getStartTravelInfos(from);
        startTravelInfos.forEach(t->checkNext(t, to, from));
        List<TravelInfo> allResults = getAllTravelInfos();
        allTransports = new ArrayList<>();
        sortTransports(from, to, allResults);
        List<CountryData> result = new ArrayList<>();
        allTransports.forEach(transport->{
            TravelInfo fromInfo = transport.get(0);
            int transferCount = transport.size()-1;
            TravelInfo toInfo = transport.get(transferCount);
            Integer time = transport.stream().mapToInt(TravelInfo::getTravelTime).sum();
            Double distance = round(transport.stream().mapToDouble(TravelInfo::getTravelDistance).sum(), 2);
            result.add(new CountryData(fromInfo, toInfo, transferCount, time, distance, transport));
        });
        return result;
    }

    private void sortTransports(Settlement from, Settlement to, List<TravelInfo> allResults) {
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
        sortedFirsts.forEach(f->{
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
