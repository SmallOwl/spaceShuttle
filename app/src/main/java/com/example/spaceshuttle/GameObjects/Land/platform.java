package com.example.spaceshuttle.GameObjects.Land;

public class platform {

    private int platformStart;
    private int platformEnd;
    private int platformValue;
    private boolean station;

    public int getPlatformStart() {
        return platformStart;
    }

    public void setPlatformStart(int platformStart) {
        this.platformStart = platformStart;
    }

    public int getPlatformEnd() {
        return platformEnd;
    }

    public void setPlatformEnd(int platformEnd) {
        this.platformEnd = platformEnd;
    }

    public int getPlatformValue() {
        return platformValue;
    }

    public void setPlatformValue(int platformValue) {
        this.platformValue = platformValue;
    }

    public boolean isStation() {
        return station;
    }

    public void setStation(boolean station) {
        this.station = station;
    }
}
