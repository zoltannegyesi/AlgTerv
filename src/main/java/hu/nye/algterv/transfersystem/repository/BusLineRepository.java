package hu.nye.algterv.transfersystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hu.nye.algterv.transfersystem.model.bus.BusLine;

public interface BusLineRepository extends CrudRepository<BusLine, Integer>{
    List<BusLine> findAll();
}
