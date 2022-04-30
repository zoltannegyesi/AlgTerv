package hu.nye.algterv.transfersystem.model.region;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "settlements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer settlementId;

    private String settlementName;

    private String settlementType;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id", referencedColumnName = "countryId")
    private Country countryId;

    private Integer airportNumber;

    private Integer trainStationNumber;

    private Integer busStationNumber;

    private Integer shipStationNumber;
}
