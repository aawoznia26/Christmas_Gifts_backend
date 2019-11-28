package com.draw.gifts.domain;

import lombok.Getter;

@Getter
public class User {

    private String name;

    private boolean excludedFromDrawing;
    private boolean ifHasDrawn;
    private boolean ifWasDrawn;

    public User(String name, boolean excludedFromDrawing, boolean ifHasDrawn, boolean ifWasDrawn) {
        this.name = name;
        this.excludedFromDrawing = excludedFromDrawing;
        this.ifHasDrawn = ifHasDrawn;
        this.ifWasDrawn = ifWasDrawn;
    }

    public void setExcludedFromDrawing(boolean excludedFromDrawing) {
        this.excludedFromDrawing = excludedFromDrawing;
    }

    public void setIfHasDrawn(boolean ifHasDrawn) {
        this.ifHasDrawn = ifHasDrawn;
    }

    public void setIfWasDrawn(boolean ifWasDrawn) {
        this.ifWasDrawn = ifWasDrawn;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", excludedFromDrawing=" + excludedFromDrawing +
                ", ifHasDrawn=" + ifHasDrawn +
                ", ifWasDrawn=" + ifWasDrawn +
                '}';
    }
}
