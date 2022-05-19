package hu.nye.algterv.transfersystem.model.data;

import hu.nye.algterv.transfersystem.model.plane.Flight;
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

    public CountryData(Flight from, Flight to, Integer transferCount, Integer time, Double distance) {
        this.fromCountry = from.getAirportId1().getSettlementId().getCountryId().getCountryName();
        this.toCountry = to.getAirportId2().getSettlementId().getCountryId().getCountryName();
        this.transferCount = transferCount;
        this.time = time;
        this.distance = distance;
    }
}
