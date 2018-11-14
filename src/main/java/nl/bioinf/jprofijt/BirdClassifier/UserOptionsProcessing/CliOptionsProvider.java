/*
 * Copyright (c) 2018 Jouke Profijt
 * Licensed under GPLv3. See LICENSE
 */
package nl.bioinf.jprofijt.BirdClassifier.UserOptionsProcessing;

import org.apache.commons.cli.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Class that parses commandline for the OptionsProvider interface
 *
 * @author jouke profijt
 */

public class CliOptionsProvider implements OptionsProvider {
    private static final String FILE = "file";
    private static final String OUT = "out";
    private static final String HELP = "help";
    private static final String CSV = "csv";

    private final String[] commandlineArguments;
    private Options options;
    private CommandLine commandLine;
    private String defaultOutput;


    public CliOptionsProvider(final String[] args) {
        /**
         * get arguments & initializes Options Provider
         */
        this.commandlineArguments = args;
        initialize();

    }

    private void initialize() {
        /**
         * builds available options & starts the processing of the commandline
         */
        buildOptions();
        processCommandline();

    }

    public boolean helpRequested() {
        /**
         * if help is requested or commandline is empty return true
         *
         * @return boolean if help is needed
         */
        if (commandLine.hasOption(HELP)) {
            return true;
        } else return false;

    }

    private void buildOptions() {
        /**
         * Creates options object and adds necessary options.
         *
         * @HELP to give the user the option to request help
         * @CSV if the output should be in CSV format
         * @FILE path to classification file
         * @OUT optional output file path
         */
        this.options = new Options();
        Option helpOption = new Option("h", HELP, false, "Prints this Message");
        Option typeOption = new Option("c", CSV, false, "Sets the output type to csv");
        Option fileOption = new Option("f", FILE, true, "/path/to/file");
        Option outOption = new Option("o", OUT, true, "output file location. /path/to/output. default BirdclassificationResults.arff or .csv if that option is given");

        options.addOption(helpOption);
        options.addOption(fileOption);
        options.addOption(outOption);
        options.addOption(typeOption);
    }

    private void processCommandline() throws IllegalStateException {
        /**
         * processCommandline reads commandline,
         * checks input file & sets the outfile
         */
        try {
            CommandLineParser parser = new DefaultParser();
            this.commandLine = parser.parse(this.options, this.commandlineArguments);
                if (commandLine.hasOption(FILE)) {
                    if (commandLine.hasOption(CSV)) {
                        this.defaultOutput = "BirdClassificationResults.csv";

                    } else this.defaultOutput = "BirdClassificationResults.arff";

                    try {
                        //Try to create a new file to see if it exists
                        File f = new File(getDataFile());
                        FileInputStream inputStream = new FileInputStream(f);
                    } catch (FileNotFoundException ex) {
                        //if it doesn't it throws FileNotFoundException and handle accordingly
                        throw new IllegalStateException("File: \"" + getDataFile() + "\" cannot be found");
                    }
                }


        } catch (ParseException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public void printHelp() {
        /**
         * Print help in readable format
         */
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("BirdClassifier", options);
    }

    @Override
    public String getDataFile() {
        return this.commandLine.getOptionValue(FILE);
    }

    @Override
    public String getOutputFile() {
        return this.commandLine.getOptionValue(OUT, this.defaultOutput);
    }

    @Override
    public boolean setOutputCSV() {
        return commandLine.hasOption(CSV);
    }

}

