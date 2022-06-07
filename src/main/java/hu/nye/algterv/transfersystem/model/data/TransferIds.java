package hu.nye.algterv.transfersystem.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferIds {
    private Integer airportId;
    private Integer busStationId;
    private Integer trainStationId;
    private Integer shipStationId;

    public Boolean isEmpty() {
        return airportId == null && busStationId == null && trainStationId == null && shipStationId == null;
    }
}
