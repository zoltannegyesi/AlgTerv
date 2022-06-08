package hu.nye.algterv.transfersystem.model.data;

import hu.nye.algterv.transfersystem.model.region.Settlement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferIds {
    private Settlement airportSettlement;
    private Settlement busStationSettlement;
    private Settlement trainStationSettlement;
    private Settlement shipStationSettlement;

    public Boolean isEmpty() {
        return airportSettlement == null && busStationSettlement == null && trainStationSettlement == null && shipStationSettlement == null;
    }
}
