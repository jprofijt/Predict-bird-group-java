/*
 * Copyright (c) 2018 Jouke Profijt
 * Licensed under GPLv3. See LICENSE
 */
package nl.bioinf.jprofijt.BirdClassifier.UserOptionsProcessing;


import nl.bioinf.jprofijt.BirdClassifier.BoneClassification.BirdClassifier;

import java.util.Arrays;

public final class CliArgumentsRunner {

    private CliArgumentsRunner() {

    }


    public static void main(String[] args) {
        try {
            CliOptionsProvider options = new CliOptionsProvider(args);
            if (options.helpRequested()) {
                options.printHelp();
                return;
            } else {
               BirdClassifier Birds = new BirdClassifier();
               Birds.setUnknownFile(options.getDataFile());
               Birds.setOutputFile(options.getOutputFile());

               Birds.CSV = options.setOutputCSV();
               Birds.start();

            }
        } catch (IllegalStateException ex) {
            System.err.println("An error has occured while processing your commandline \"" + Arrays.toString(args) + "\"");
            System.err.println("Parsing failed. Reason: " + ex.getMessage());
            CliOptionsProvider options = new CliOptionsProvider(new String[]{});
            options.printHelp();
        }
    }
}
