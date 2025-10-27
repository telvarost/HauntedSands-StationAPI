package com.github.telvarost.hauntedsands.enums;

public enum AdobeRecipeEnum {
    NO_RECIPE("No Recipe"),
    OLD_RECIPE("Old: Dirt"),
    NEW_RECIPE("New: Dirt And Sand"),
    REALISM_RECIPE("Realism: Dirt, Sand, Wheat, Water");

    final String stringValue;

    AdobeRecipeEnum() {
        this.stringValue = "New: Dirt And Sand";
    }

    AdobeRecipeEnum(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}