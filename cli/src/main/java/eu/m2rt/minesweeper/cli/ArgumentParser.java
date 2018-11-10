package eu.m2rt.minesweeper.cli;

import org.apache.commons.cli.*;

class ArgumentParser {

    static Args parse(String... args) {
        Options options = new Options();

        options.addOption(
                Option  .builder("w")
                        .longOpt("width")
                        .hasArg()
                        .optionalArg(true)

                        .build()
        );

        options.addOption(
                Option  .builder("h")
                        .longOpt("height")
                        .hasArg()
                        .optionalArg(true)
                        .build()
        );

        options.addOption(
                Option  .builder("b")
                        .longOpt("bombs")
                        .hasArg()
                        .optionalArg(true)
                        .build()
        );

        options.addOption(
                Option  .builder()
                        .longOpt("help")
                        .desc("Show this message")
                        .build()
        );

        try {
            return new Args(new DefaultParser().parse(options, args), options);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static class Args {
        private final CommandLine cl;
        private final Options options;
        final int width, height, bombs;
        final boolean hasHelp;

        private Args(CommandLine cl, Options options) {
            this.cl = cl;
            this.options = options;
            width = getValue("width", 10);
            height = getValue("height", 6);
            bombs = getValue("bombs", 10);
            hasHelp = cl.hasOption("help");
        }

        private int getValue(String name, int defaultValue) {
            if (cl.hasOption(name)) return Integer.parseInt(cl.getOptionValue(name));
            return defaultValue;
        }

        void printHelp() {
            new HelpFormatter().printHelp("cli", options);
        }
    }
}
