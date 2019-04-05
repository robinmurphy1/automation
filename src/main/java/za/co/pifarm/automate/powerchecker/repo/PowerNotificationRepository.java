package za.co.pifarm.automate.powerchecker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.pifarm.automate.powerchecker.data.PowerNotification;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;

@Repository
public interface PowerNotificationRepository extends JpaRepository<PowerNotification, RemoteLocation> {

}
