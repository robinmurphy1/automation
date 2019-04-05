package za.co.pifarm.automate.powerchecker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.pifarm.automate.powerchecker.data.PowerData;

import java.util.Date;
import java.util.List;

@Repository
public interface PowerDataRepository extends JpaRepository<PowerData, Long> {

    @Query(value = "from POWER_DATA t where timestamp BETWEEN :startTime AND :endTime")
    List<PowerData> findPowerDataByTimestampBetweenOrderByTimestampDesc(@Param("startTime")Date startTime, @Param("endTime") Date endTime);
}
