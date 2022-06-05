package hu.nye.algterv.transfersystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hu.nye.algterv.transfersystem.model.ship.ShipLine;

public interface ShipLineRepository extends CrudRepository<ShipLine, Integer> {
    List<ShipLine> findAll();
}
