/*
 * Copyright (c) 2018 Jouke Profijt
 * Licensed under GPLv3. See LICENSE
 */
package nl.bioinf.jprofijt.BirdClassifier.UserOptionsProcessing;

import org.apache.commons.cli.*;
import weka.core.pmml.jaxbbindings.True;

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
        this.commandlineArguments = args;
        initialize();

    }

    private void initialize() {
        buildOptions();
        processCommandline();

    }

    public boolean helpRequested() {
        if (commandLine.hasOption(HELP)) {
            return true;
        } else if (commandLine.getArgs().length == 0) {
            return true;
        } else return false;

    }

    private void buildOptions() {
        this.options = new Options();
        Option helpOption = new Option("h", HELP, false, "Prints this Message");
        Option typeOption = new Option("c", CSV, false, "Sets the output type to csv");
        Option fileOption = new Option("f", FILE, true, "/path/to/file");
        Option outOption = new Option("o", OUT, true, "output file location. /path/to/output. default BirdclassificationResults.arff or .csv if that option is given");

        fileOption.setRequired(true);

        options.addOption(helpOption);
        options.addOption(fileOption);
        options.addOption(outOption);
        options.addOption(typeOption);
    }

    private void processCommandline() throws IllegalStateException {
        try {
            CommandLineParser parser = new DefaultParser();
            this.options.
            this.commandLine = parser.parse(this.options, this.commandlineArguments);
                if (commandLine.hasOption(FILE)) {
                    if (commandLine.hasOption(CSV)) {
                        this.defaultOutput = "BirdClassificationResults.csv";

                    } else this.defaultOutput = "BirdClassificationResults.arff";

                    try {
                        File f = new File(commandLine.getOptionValue(FILE));
                        FileInputStream inputStream = new FileInputStream(f);
                    } catch (FileNotFoundException ex) {
                        throw new IllegalStateException(ex);
                    }
                }


        } catch (ParseException ex) {
            throw new IllegalStateException(ex);
        }
    }

/*
    private boolean checkFile(String file){
        //should check if the file has the correct filetype.
        File f = new File(file);
            if (f.toString().contains(".csv") | f.toString().contains(".arff")) {
                try {
                    if (f.exists() && f.canRead()) {
                        //Checks if file exists
                        return true;

                    } else {
                        throw new NoSuchFileException("Given datafile can not be found, Given the correct path?");
                        return false;
                    }
                } catch (NoSuchFileException ex) {
                    throw new NoSuchFileException(ex);
                }
            } else return false;


*/


    //   }

    public void printHelp() {
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

