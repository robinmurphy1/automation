package za.co.pifarm.automate.powerchecker.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.pifarm.automate.powerchecker.data.PowerNotification;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;

@Repository
public interface PowerNotificationRepository extends CrudRepository<PowerNotification, RemoteLocation> {

}
