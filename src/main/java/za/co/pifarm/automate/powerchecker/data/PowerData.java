package za.co.pifarm.automate.powerchecker.data;


import org.hibernate.annotations.Proxy;
import za.co.pifarm.automate.powerchecker.enums.PowerStatus;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;
import za.co.pifarm.automate.powerchecker.model.LocationData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity(name = "POWER_DATA")
@Table(name = "POWER_DATA", schema = "POWER")
@Proxy(lazy=false)
public class PowerData {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "TIMESTAMP")
    private Date timestamp;

    @Column(name = "LOCATION")
    @Enumerated
    private RemoteLocation remoteLocation;

    @Column(name = "STATUS")
    @Enumerated
    private PowerStatus powerStatus;

    public PowerData(Date timestamp, RemoteLocation remoteLocation, PowerStatus powerStatus) {
        this.timestamp = timestamp;
        this.remoteLocation = remoteLocation;
        this.powerStatus = powerStatus;
    }

    public PowerData() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public RemoteLocation getRemoteLocation() {
        return remoteLocation;
    }

    public void setRemoteLocation(RemoteLocation remoteLocation) {
        this.remoteLocation = remoteLocation;
    }

    public PowerStatus getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(PowerStatus powerStatus) {
        this.powerStatus = powerStatus;
    }
}
