package hu.nye.algterv.transfersystem.repository;

import org.springframework.data.repository.CrudRepository;

import hu.nye.algterv.transfersystem.model.plane.Airport;

public interface AirportRepository extends CrudRepository<Airport, Integer> {
    
}
