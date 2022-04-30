package hu.nye.algterv.transfersystem.model.region;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class NeightbourPK implements Serializable{

    private Integer countryId1;
    
    private Integer countryId2;

}
