package hu.nye.algterv.transfersystem.model.train;

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
@Table(name = "train_lines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TrainLinePK.class)
public class TrainLine {
    
    @Id
    @OneToOne
    @JoinColumn(name = "train_station_id_1", referencedColumnName = "trainStationId")
    private TrainStation trainStationId1;

    @Id
    @OneToOne
    @JoinColumn(name = "trainStation_id_2", referencedColumnName = "trainStationId")
    private TrainStation trainStationId2;

    private Double travelDistance;

    private Integer travelTime;
}
