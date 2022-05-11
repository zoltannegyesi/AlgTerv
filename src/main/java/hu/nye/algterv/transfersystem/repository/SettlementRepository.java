package hu.nye.algterv.transfersystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hu.nye.algterv.transfersystem.model.region.Settlement;

public interface SettlementRepository extends CrudRepository<Settlement, Integer> {
    List<Settlement> findAll();

    Settlement findBySettlementName(String settlementName);
}
