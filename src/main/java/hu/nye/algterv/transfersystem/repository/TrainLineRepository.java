package hu.nye.algterv.transfersystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hu.nye.algterv.transfersystem.model.train.TrainLine;

public interface TrainLineRepository extends CrudRepository<TrainLine, Integer>{
    List<TrainLine> findAll();
}
