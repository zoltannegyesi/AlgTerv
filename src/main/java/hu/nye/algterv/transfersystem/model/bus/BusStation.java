package hu.nye.algterv.transfersystem.model.bus;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BusLine> settlementId;

    private String gps1;

    private String gps2;
}
