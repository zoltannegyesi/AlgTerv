package hu.nye.algterv.transfersystem.model.train;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class TrainLinePK implements Serializable{

    private Integer trainStationId1;
    
    private Integer trainStationId2;
}
