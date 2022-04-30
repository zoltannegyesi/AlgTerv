package hu.nye.algterv.transfersystem.model.bus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class BusLinePK {
    
    private Integer busStationId1;
    
    private Integer busStationId2;

}
