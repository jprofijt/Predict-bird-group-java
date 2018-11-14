/*
 * Copyright (c) 2018 Jouke Profijt
 * Licensed under GPLv3. See LICENSE
 */
package nl.bioinf.jprofijt.BirdClassifier.UserOptionsProcessing;


import nl.bioinf.jprofijt.BirdClassifier.BoneClassification.BirdClassifier;

import java.util.Arrays;

public final class CliArgumentsRunner {
    /**
     * main class for commandline application
     *
     * gets commandline arguments, processes them
     * & feeds them to a BirdClassifier object
     */

    private CliArgumentsRunner() {

    }


    public static void main(String[] args) {
        /**
         * main module of CliArgumentsRunner
         *
         * calls Options Provider
         * Checks if help is requested or needed
         * then calls BirdClassifier
         * @args commandline arguments
         */
        try {
            CliOptionsProvider options = new CliOptionsProvider(args);
            if (options.helpRequested()) {
                options.printHelp();
                return;
            } else {
               BirdClassifier Birds = new BirdClassifier();
               //set input and output files
               Birds.setUnknownFile(options.getDataFile());
               Birds.setOutputFile(options.getOutputFile());

               //check if csv output is needed
               Birds.CSV = options.setOutputCSV();
               //start classification process
               Birds.start();

            }
        } catch (IllegalStateException ex) {
            System.err.println("An error has occured while processing your commandline \"" + Arrays.toString(args) + "\"");
            System.err.println("Parsing failed. Reason: " + ex.getMessage());
            //currently doesn't print help as this caused more exceptions because of required argument -f.

        }
    }
}
