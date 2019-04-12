package za.co.pifarm.automate.powerchecker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.pifarm.automate.powerchecker.data.PowerData;

import java.util.Date;
import java.util.List;

@Repository
public interface PowerDataRepository extends JpaRepository<PowerData, Long> {

    List<PowerData> findPowerDataByTimestampBetweenOrderByTimestampDesc(Date startTime, Date endTime);

// @Query(value = "from powerData t where timestamp BETWEEN :startTime AND :endTime", nativeQuery = false)
//    List<PowerData> findPowerDataByTimestampBetweenOrderByTimestampDesc(@Param("startTime")Date startTime, @Param("endTime") Date endTime);
}
