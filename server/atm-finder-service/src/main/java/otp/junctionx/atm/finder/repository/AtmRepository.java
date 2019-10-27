package otp.junctionx.atm.finder.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import otp.junctionx.atm.finder.model.Atm;

import java.util.List;
import java.util.Optional;

public interface AtmRepository extends PagingAndSortingRepository<Atm, String> {

    @Override
    Optional<Atm> findById(String id);

    @Override
    List<Atm> findAll();

}
