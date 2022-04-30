package hu.nye.algterv.transfersystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    
    @Id
    private Long countryId;
    private String countryName;
    private Long regionId;
    private Long settlementNumber;
    private Long airportNumber;
    private Long trainStationNumber;
    private Long busStationNumber;
    private Long shipStationNumber;
}
