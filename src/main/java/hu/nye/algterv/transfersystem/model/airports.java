package hu.nye.algterv.transfersystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "airports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Airports {
    @Id
    private Long airportId;
    private String airportInternationalCode;
    private String airportName;
    private Long settlementId;
    private String GPS1;
    private String GPS2;

}
