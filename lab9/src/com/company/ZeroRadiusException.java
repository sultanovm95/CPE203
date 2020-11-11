package com.company;

import com.company.CircleException;

public class ZeroRadiusException extends CircleException {

    public ZeroRadiusException() {
        super("zero radius");
    }
}
