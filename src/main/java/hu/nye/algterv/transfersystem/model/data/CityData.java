package hu.nye.algterv.transfersystem.model.data;

import java.util.List;

import hu.nye.algterv.transfersystem.model.TravelInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityData {
    
    private Integer id;
    private String fromCity;
    private String toCity;
    private Integer transferCount;
    private Integer time;
    private Double distance;
    private List<TravelInfo> travels;

    public CityData(Integer id, TravelInfo from, TravelInfo to, Integer transferCount, Integer time, Double distance, List<TravelInfo> travels) {
        this.id = id;
        this.fromCity = from.getFromSettlement().getSettlementName();
        this.toCity = to.getToSettlement().getSettlementName();
        this.transferCount = transferCount;
        this.time = time;
        this.distance = distance;
        this.travels = travels;
    }
}
