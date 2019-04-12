package za.co.pifarm.automate.powerchecker;

import za.co.pifarm.automate.powerchecker.data.PowerData;
import za.co.pifarm.automate.powerchecker.enums.PowerStatus;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Data {

    public static List<PowerData> createPowerDataWithinThreshold() {

        List<PowerData> powerDataList = new ArrayList<PowerData>();
        powerDataList.add(new PowerData(dateConverter(-0), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-10), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-20), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-29), RemoteLocation.HOME, PowerStatus.ERR));

        return powerDataList;
    }

    public static List<PowerData> createPowerDataWithinAndOutThreshold() {

        List<PowerData> powerDataList = new ArrayList<PowerData>();
        powerDataList.add(new PowerData(dateConverter(1), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-0), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-10), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-40), RemoteLocation.HOME, PowerStatus.OK));
        powerDataList.add(new PowerData(dateConverter(-32), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-31), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-33), RemoteLocation.HOME, PowerStatus.ERR));

        return powerDataList;
    }

    public static List<PowerData> createPowerDataNotWithinThreshold() {

        List<PowerData> powerDataList = new ArrayList<PowerData>();
        powerDataList.add(new PowerData(dateConverter(1), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(2), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-35), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-39), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-40), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-50), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-60), RemoteLocation.HOME, PowerStatus.ERR));

        return powerDataList;
    }

    public static List<PowerData> createPowerDataFirstEntryWithinThreshold() {

        List<PowerData> powerDataList = new ArrayList<PowerData>();
        powerDataList.add(new PowerData(dateConverter(1), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(2), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-1), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-39), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-40), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-50), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-60), RemoteLocation.HOME, PowerStatus.ERR));

        return powerDataList;
    }

    public static List<PowerData> createPowerDataThreeEntryErrWithinThreshold() {

        List<PowerData> powerDataList = new ArrayList<PowerData>();
        powerDataList.add(new PowerData(dateConverter(1), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(2), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-1), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-10), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-20), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-30), RemoteLocation.HOME, PowerStatus.ERR));
        powerDataList.add(new PowerData(dateConverter(-60), RemoteLocation.HOME, PowerStatus.OK));

        return powerDataList;
    }

    static Date dateConverter(long minutes) {
        return Date.from(LocalDateTime.now().atZone(ZoneId.of("Africa/Johannesburg")).toInstant().plusSeconds(minutes * 60));
    }
}