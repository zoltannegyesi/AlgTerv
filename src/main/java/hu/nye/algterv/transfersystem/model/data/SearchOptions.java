package hu.nye.algterv.transfersystem.model.data;

import hu.nye.algterv.transfersystem.model.SearchingData;
import lombok.Data;

@Data
public class SearchOptions {
    private Boolean isPlane;
    private Boolean isBus;
    private Boolean isTrain;
    private Boolean isShip;

    public SearchOptions(SearchingData searchingData) {
        this.isPlane = searchingData.getPlane() != null;
        this.isBus = searchingData.getBus() != null;
        this.isTrain = searchingData.getTrain() != null;
        this.isShip = searchingData.getShip() != null;
    }

    public Boolean isNotEmpty() {
        return this.isPlane || this.isBus || this.isTrain || this.isShip;
    }
}
