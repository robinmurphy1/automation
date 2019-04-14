package za.co.pifarm.automate.powerchecker.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;
import za.co.pifarm.automate.powerchecker.model.LocationData;


@Component
public class PowerDataMapper extends DataMapper {

    private @Autowired
    Environment environment;

    public LocationData getLocationData(RemoteLocation remoteLocation) {

        String locationUrl = environment.getProperty(String.format("power.check.location.%s", remoteLocation.getLocation()));

        return new LocationData(locationUrl, remoteLocation);
    }
}
