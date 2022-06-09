package hu.nye.algterv.transfersystem.model.data;

import java.util.List;

import hu.nye.algterv.transfersystem.model.TravelInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryData {
    
    private String fromCountry;
    private String toCountry;
    private Integer transferCount;
    private Integer time;
    private Double distance;
    private List<TravelInfo> travels;

    public CountryData(TravelInfo from, TravelInfo to, Integer transferCount, Integer time, Double distance, List<TravelInfo> travels) {
        this.fromCountry = from.getFromSettlement().getSettlementName();
        this.toCountry = to.getToSettlement().getSettlementName();
        this.transferCount = transferCount;
        this.time = time;
        this.distance = distance;
        this.travels = travels;
    }
}
