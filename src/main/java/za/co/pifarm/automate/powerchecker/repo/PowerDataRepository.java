package za.co.pifarm.automate.powerchecker.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.pifarm.automate.powerchecker.data.PowerData;

@Repository
public interface PowerDataRepository extends CrudRepository<PowerData, Long> {
}
