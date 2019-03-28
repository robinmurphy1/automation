package za.co.pifarm.automate.powerchecker.enums;

public enum RemoteLocation {

    HOME("home");

    private String location;

    RemoteLocation(String location) {

        this.location = location;
    }

    public String getLocation() {

        return location;
    }
}
