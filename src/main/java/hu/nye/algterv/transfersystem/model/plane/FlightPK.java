package hu.nye.algterv.transfersystem.model.plane;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class FlightPK implements Serializable{
    
    private Long airportId1;
    private Long airportId2;
    
}
