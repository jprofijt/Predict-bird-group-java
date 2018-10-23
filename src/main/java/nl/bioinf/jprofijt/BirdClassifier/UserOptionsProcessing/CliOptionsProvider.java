/**
 * Copyright (c) 2018 Jouke Profijt
 * Licensed under GPLv3. See LICENSE
 */
package nl.bioinf.jprofijt.BirdClassifier.UserOptionsProcessing;

import nl.bioinf.jprofijt.BirdClassifier.BoneClassification.FileParser;
import org.apache.commons.cli.*;

import weka.core.pmml.jaxbbindings.True;


import java.io.File;

/**
 * Class that parses commandline for the OptionsProvider interface
 *
 * @author jouke
 */

public class CliOptionsProvider implements OptionsProvider {
    private static final String FILE = "file";
    private static final String OUT = "out";
    private static final String HELP = "help";

    private final String[] commandlineArguments;
    private Options options;
    private CommandLine commandLine;
    private String file;


    public CliOptionsProvider(final String[] args){
        this.commandlineArguments = args;
        initialize();

    }

    private void initialize(){
        buildOptions();
        processCommandline();

    }

    public boolean helpRequested(){
        return this.commandLine.hasOption(HELP);
    }

    private void buildOptions() {
        this.options = new Options();
        Option helpOption = new Option("h", HELP, false,"Prints this Message");
        Option fileOption = new Option("f", FILE, true, "/path/to/file");
        Option outOption = new Option("o", OUT, true, "output file format. Default .arff");

        options.addOption(helpOption);
        options.addOption(fileOption);
        options.addOption(outOption);
    }

    private void processCommandline() {
        try {
            CommandLineParser parser = new DefaultParser();
            this.commandLine = parser.parse(this.options, this.commandlineArguments);

            if (commandLine.hasOption(FILE)) {
                String f = commandLine.getOptionValue(FILE);
                if (checkFile(f)) {
                    this.file = f;

                } else {
                    throw new IllegalArgumentException("File is unreadable"); // placeholder
                }

            }
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private boolean checkFile(String file){
        //should check if the file has the correct datatypes and or it exists.
        File f = new File(file);


        return true;




    }
}


