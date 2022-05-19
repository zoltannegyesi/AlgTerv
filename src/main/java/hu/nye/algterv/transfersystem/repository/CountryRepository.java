package hu.nye.algterv.transfersystem.repository;

import org.springframework.data.repository.CrudRepository;

import hu.nye.algterv.transfersystem.model.region.Country;

public interface CountryRepository extends CrudRepository<Country, Integer>{
    
}
