package com.example.spaceshuttle.GameObjects.Land;

import com.example.spaceshuttle.GameObjects.FuelStation.fuelStation;

import java.util.List;

public class land {

    private int[] landPointers;
    private List<platform> platforms;
    private List<fuelStation> stations;

    public int[] getLandPointers() {
        return landPointers;
    }

    public void setLandPointers(int[] landPointers) {
        this.landPointers = landPointers;
    }

    public List<platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<platform> platforms) {
        this.platforms = platforms;
    }

    public List<fuelStation> getStations() {
        return stations;
    }

    public void setStations(List<fuelStation> stations) {
        this.stations = stations;
    }
}
