package za.co.pifarm.automate.powerchecker.model;

import org.springframework.http.HttpMethod;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;

public class LocationData {

    private String remoteUrl;
    private RemoteLocation remoteLocation;
    private HttpMethod httpMethod;

    public LocationData(String remoteUrl, RemoteLocation remoteLocation) {
        this.remoteUrl = remoteUrl;
        this.remoteLocation = remoteLocation;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public RemoteLocation getRemoteLocation() {
        return remoteLocation;
    }

    public void setRemoteLocation(RemoteLocation remoteLocation) {
        this.remoteLocation = remoteLocation;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }
}
