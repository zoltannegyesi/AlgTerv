package hu.nye.algterv.transfersystem.model.bus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bus_stations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusStation {
    
    @Id
    private Long busStationId;
    private String busStationName;
    private Long settlementId;
    private String gps1;
    private String gps2;
}
