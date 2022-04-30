package hu.nye.algterv.transfersystem.model.ship;

import java.io.Serializable;
import java.time.LocalTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class ShipLinePK implements Serializable{

    private Integer shipStationId1;
    
    private Integer shipStationId2;
}

