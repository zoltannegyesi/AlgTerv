package hu.nye.algterv.transfersystem.model;

import hu.nye.algterv.transfersystem.model.bus.BusLine;
import hu.nye.algterv.transfersystem.model.plane.Flight;
import hu.nye.algterv.transfersystem.model.region.Settlement;
import hu.nye.algterv.transfersystem.model.ship.ShipLine;
import hu.nye.algterv.transfersystem.model.train.TrainLine;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TravelInfo {
    
    private Settlement fromSettlement;

    private Settlement toSettlement;

    private String transportType;

    private Double travelDistance;

    private Integer travelTime;


    public TravelInfo(BusLine busLine) {
        this.fromSettlement = busLine.getBusStationId1().getSettlementId();
        this.toSettlement = busLine.getBusStationId2().getSettlementId();
        this.transportType = "Bus";
        this.travelDistance = busLine.getTravelDistance();
        this.travelTime = busLine.getTravelTime();
    }

    public TravelInfo(Flight flight) {
        this.fromSettlement = flight.getAirportId1().getSettlementId();
        this.toSettlement = flight.getAirportId2().getSettlementId();
        this.transportType = "Plane";
        this.travelDistance = flight.getTravelDistance();
        this.travelTime = flight.getTravelTime();
    }

    public TravelInfo(TrainLine trainLine) {
        this.fromSettlement = trainLine.getTrainStationId1().getSettlementId();
        this.toSettlement = trainLine.getTrainStationId2().getSettlementId();
        this.transportType = "Train";
        this.travelDistance = trainLine.getTravelDistance();
        this.travelTime = trainLine.getTravelTime();
    }

    public TravelInfo(ShipLine shipLine) {
        this.fromSettlement = shipLine.getShipStationId1().getSettlementId();
        this.toSettlement = shipLine.getShipStationId2().getSettlementId();
        this.transportType = "Ship";
        this.travelDistance = shipLine.getTravelDistance();
        this.travelTime = shipLine.getTravelTime();
    }
}
