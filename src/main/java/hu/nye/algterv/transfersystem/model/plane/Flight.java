package hu.nye.algterv.transfersystem.model.plane;

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
import lombok.ToString;

@Entity
@Table(name = "flights")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FlightPK.class)
public class Flight {
    
    @Id
    @OneToOne
    @JoinColumn(name = "airport_id_1", referencedColumnName = "airportId")
    private Airport airportId1;

    @Id
    @OneToOne
    @JoinColumn(name = "airport_id_2", referencedColumnName = "airportId")
    private Airport airportId2;

    private Double travelDistance;

    private Integer travelTime;
}
