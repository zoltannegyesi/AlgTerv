package hu.nye.algterv.transfersystem.model.region;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "neightbours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Neightbour {

    @Id
    @OneToOne
    @JoinColumn(name = "country_id_1", referencedColumnName = "countryId")
    private Country countryId1;

    @Id
    @OneToOne
    @JoinColumn(name = "country_id_2", referencedColumnName = "countryId")
    private Country countryId2;
}
