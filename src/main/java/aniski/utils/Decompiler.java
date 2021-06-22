package aniski.utils;

import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;
import org.benf.cfr.reader.util.getopt.GetOptParser;
import org.benf.cfr.reader.util.getopt.Options;
import org.benf.cfr.reader.util.getopt.OptionsImpl;

import java.util.Arrays;
import java.util.List;

public class Decompiler {

    public static void main(String[] args) {
        Decompiler decompiler = new Decompiler();
        decompiler.init();
    }

    private void init() {

        String[] parse = new String[]{"data/client.jar", "--outputdir", "data/output"};

        System.out.println(Arrays.toString(parse));
        GetOptParser getOptParser = new GetOptParser();
        Options options = null;
        List<String> files = null;

        try {
            Pair<List<String>, Options> processedArgs = getOptParser.parse(parse, OptionsImpl.getFactory());
            System.out.println(processedArgs.toString());
            files = processedArgs.getFirst();
            options = processedArgs.getSecond();
            if (files.size() == 0) {
                throw new IllegalArgumentException("Insufficient unqualified parameters - provide at least one filename.");
            }
        } catch (Exception var5) {
            getOptParser.showHelp(var5);
            System.exit(1);
        }

        if (!options.optionIsSet(OptionsImpl.HELP) && !files.isEmpty()) {
            CfrDriver cfrDriver = (new CfrDriver.Builder()).withBuiltOptions(options).build();
            cfrDriver.analyse(files);
        } else {
            getOptParser.showOptionHelp(OptionsImpl.getFactory(), options, OptionsImpl.HELP);
        }
    }
}
