/*
 * Copyright (c) 2018 Jouke Profijt
 * Licensed under GPLv3. See LICENSE
 */
package nl.bioinf.jprofijt.BirdClassifier.UserOptionsProcessing;

/**
 * Options provider interface
 * to be used for interaction with bird classification
 *
 * @author Jouke Profijt
 */

public interface OptionsProvider {
    /**
     * Gives the file used for classification
     * @return datafile
     */
    String getDataFile();

    /**
     * Gives the location for the output from the classification results.
     * @return path/to/newfile
     */
    String getOutputFile();

    /**
     * Sets a boolean value if the output needs to be in csv format
     * @return boolean if output needs to be csv
     */
    boolean setOutputCSV();

}
