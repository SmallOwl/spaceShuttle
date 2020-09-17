package com.example.spaceshuttle.GameObjects.Land;

import android.graphics.Canvas;

import com.example.spaceshuttle.GameObjects.gameObject;

import java.util.List;

public class land {

    private int[] landPointers;
    private List<platform> platforms;

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

}
