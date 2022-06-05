package hu.nye.algterv.transfersystem.model;

import hu.nye.algterv.transfersystem.model.bus.BusLine;
import hu.nye.algterv.transfersystem.model.plane.Flight;
import hu.nye.algterv.transfersystem.model.region.Settlement;
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
}
