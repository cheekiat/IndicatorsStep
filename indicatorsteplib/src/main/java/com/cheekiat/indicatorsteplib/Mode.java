package com.cheekiat.indicatorsteplib;

/**
 * Created by chee kiat on 12/01/2018.
 */

public enum Mode {

    INDICATORS(0), STEP(1), STEP_WITH_BAR(2);

    int value;

    Mode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
