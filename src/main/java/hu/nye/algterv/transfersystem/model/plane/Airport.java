package hu.nye.algterv.transfersystem.model.plane;

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
@Table(name = "airports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer airportId;

    private String airportInternationalCode;

    private String airportName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Flight settlementId;

    private String gps1;

    private String gps2;
}
