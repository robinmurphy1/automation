package za.co.pifarm.automate.powerchecker.data;


import za.co.pifarm.automate.powerchecker.enums.PowerStatus;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "POWER_DATA", schema = "POWER")
public class PowerData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Override
    public String toString() {
        return "PowerData{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", remoteLocation=" + remoteLocation +
                ", powerStatus=" + powerStatus +
                '}';
    }
}
