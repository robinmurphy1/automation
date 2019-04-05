package za.co.pifarm.automate.powerchecker.data;

import org.hibernate.annotations.Proxy;
import za.co.pifarm.automate.powerchecker.enums.PowerStatus;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POWER_NOTIFICATION", schema = "POWER")
@Proxy(lazy=false)
public class PowerNotification {

    @Id
    @Enumerated
    private RemoteLocation remoteLocation;

    @Enumerated
    private PowerStatus status;

    public PowerNotification(RemoteLocation location, PowerStatus status) {

        this.remoteLocation = location;
        this.status = status;
    }

    public PowerNotification() {
    }

    public RemoteLocation getRemoteLocation() {
        return remoteLocation;
    }

    public void setRemoteLocation(RemoteLocation remoteLocation) {
        this.remoteLocation = remoteLocation;
    }

    public PowerStatus getStatus() {
        return status;
    }

    public void setStatus(PowerStatus status) {
        this.status = status;
    }
}
