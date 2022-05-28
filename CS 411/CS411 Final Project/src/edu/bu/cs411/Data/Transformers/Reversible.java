package edu.bu.cs411.Data.Transformers;

import java.util.function.Function;

/**
 * Abstract Class to represent all the Parsers for Saving & Loading Data.
 * Implements the Function Interface, and forces a reverse method for the Function.
 *
 * @author evanlhsu@bu.edu.
 * @version 1.0.0.
 */
public abstract class Reversible<T, S> implements Function<T, S> {

    /**
     * Reverse the Function apply().
     *
     * @param s Object to Transform.
     * @return Transformed Object.
     */
    public abstract T reverse(S s);

}
