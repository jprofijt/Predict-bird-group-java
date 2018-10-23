package nl.bioinf.jprofijt.BirdClassifier.UserOptionsProcessing;


public final class CliArgumentsRunner {

    private CliArgumentsRunner() {

    }


    public static void main(String[] args) {
        try {
            CliOptionsProvider options = new CliOptionsProvider(args);
            if (options.helpRequested()) {
                options.printHelp();
                return;
            }
        }
    }
}
