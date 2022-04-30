package hu.nye.algterv.transfersystem.model.bus;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer busStationId;

    private String busStationName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BusLine settlementId;

    private String gps1;

    private String gps2;
}
