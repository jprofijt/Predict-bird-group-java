/*
 * Copyright (c) 2018 Jouke Profijt
 * Licensed under GPLv3. See LICENSE
 */
package nl.bioinf.jprofijt.BirdClassifier.UserOptionsProcessing;

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

}
