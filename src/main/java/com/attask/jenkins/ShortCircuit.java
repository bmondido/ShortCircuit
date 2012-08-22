package com.attask.jenkins;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * User: brianmondido
 * Date: 8/22/12
 * Time: 2:18 PM
 * <p/>
 * Exception to facilitate short circuiting of builds
 */
public class ShortCircuit extends RuntimeException {

    /**
     * Constructs a ShortCircuit with default message: 'Short circuiting the build'
     */
    public ShortCircuit() {
        super("Short circuiting the build");
    }

    /**
     * Overridden implementation to write custom messages to System.out
     */
    @Override
    public void printStackTrace() {
        this.printStackTrace(System.out);
    }

    /**
     * Overridden implementation to write custom messages to PrintStream
     */
    @Override
    public void printStackTrace(PrintStream s) {
        s.println("Ending Build. Moving on to Post-Build steps");
    }

    /**
     * Overridden implementation to write custom messages to PrintWriter
     */
    @Override
    public void printStackTrace(PrintWriter s) {
        s.println("Ending Build. Moving on to Post-Build steps");
    }
}
