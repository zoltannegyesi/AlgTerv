package hu.nye.algterv.transfersystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hu.nye.algterv.transfersystem.model.plane.Flight;

public interface FlightRepository extends CrudRepository<Flight, Integer>{
    List<Flight> findAll();
}
