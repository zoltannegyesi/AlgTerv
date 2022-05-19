package hu.nye.algterv.transfersystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchingData {

    private String departure;
    private String arrival;
    private Boolean bus;
    private Boolean ship;
    private Boolean plane;
    private Boolean train;
}
