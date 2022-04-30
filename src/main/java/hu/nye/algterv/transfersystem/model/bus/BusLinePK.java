package hu.nye.algterv.transfersystem.model.bus;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class BusLinePK implements Serializable{
    
    private Integer busStationId1;
    
    private Integer busStationId2;

}
