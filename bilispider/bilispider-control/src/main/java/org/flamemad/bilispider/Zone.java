package org.flamemad.bilispider;

public enum Zone {
    All(0),

    MAD_AMV(24,200),
    MMD_3D(25),
    AnimeMixed(27),
    StoryBoard_Dubbing(43);


    private final int zoneNumber;
    private int disturbedResolvePageNumber;
    public int getZoneNumber() {
        return zoneNumber;
    }

    Zone(int zoneNumber,int disturbedResolvePageNumber) {
        this.zoneNumber = zoneNumber;
        this.disturbedResolvePageNumber = disturbedResolvePageNumber;
    }

    Zone(int zoneNumber) {
        this.zoneNumber = zoneNumber;
    }

    public int getDisturbedResolvePageNumber() {
        return disturbedResolvePageNumber;
    }
}
