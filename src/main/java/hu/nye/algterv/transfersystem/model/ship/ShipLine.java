package hu.nye.algterv.transfersystem.model.ship;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ship_lines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ShipLinePK.class)
public class ShipLine {
    
    @Id
    @OneToOne
    @JoinColumn(name = "ship_station_id_1", referencedColumnName = "shipStationId")
    private ShipStation shipStationId1;

    @Id
    @OneToOne
    @JoinColumn(name = "ShipStation_id_2", referencedColumnName = "shipStationId")
    private ShipStation shipStationId2;

    private Double travelDistance;

    private LocalTime travelTime;
}

