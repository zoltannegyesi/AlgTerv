package hu.nye.algterv.transfersystem.model.bus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bus_lines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BusLinePK.class)
public class BusLine {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "bus_station_id_1", referencedColumnName = "busStationId")
    private BusStation busStationId1;

    @Id
    @ManyToOne
    @JoinColumn(name = "bus_station_id_2", referencedColumnName = "busStationId")
    private BusStation busStationId2;

    private Double travelDistance;

    private Long travelTime;

}
