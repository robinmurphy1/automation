package za.co.pifarm.automate.powerchecker.data;

import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class PowerNotification {

    @Id
    @Enumerated
    private RemoteLocation remoteLocation;

    private Boolean notified;

    public RemoteLocation getRemoteLocation() {
        return remoteLocation;
    }

    public void setRemoteLocation(RemoteLocation remoteLocation) {
        this.remoteLocation = remoteLocation;
    }

    public Boolean getNotified() {
        return notified;
    }

    public void setNotified(Boolean notified) {
        this.notified = notified;
    }
}
