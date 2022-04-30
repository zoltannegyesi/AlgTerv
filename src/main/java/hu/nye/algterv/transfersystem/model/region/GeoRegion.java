package hu.nye.algterv.transfersystem.model.region;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "geo_regions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeoRegion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer regionId;

    private String regionName;

    private Integer countryNumber;

    private Integer airportNumber;

    private Integer trainStationNumber;

    private Integer busStationNumber;

    private Integer shipStationNumber;
}
