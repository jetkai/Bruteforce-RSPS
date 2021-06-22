package aniski;

import aniski.flags.FlagManager;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;

import java.io.File;
import java.lang.reflect.Field;

@Command(name = "aniski", footer = "\n© 2020 Aniski Software. All rights reserved.",
        description = "\nSome description placeholder before I actually put some stuff in here. " +
                "There is going to be a little bit of description here, maybe some kind of disclaimer " +
                "to save me from getting in trouble or something like that.\n")
public class Main implements Runnable {

    @Option(names = "--help", usageHelp = true, description = "Display this help and exit")
    boolean help;

    @Option(names = {"-r", "--rsps"}, description = "Execute the rsps command structure")
    boolean rsps;

    @Option(
            names = {"-u", "--username"},
            description = "Option to crack a specific user, username must be more than 0 characters and equal/less then 12 characters"
    )
    public String username;

    @Option(
            names = {"-c", "--crack"},
            description = "Option to crack a desired user, specified within the --username option. --crack-bulk is not usable when this command is used"
    )
    boolean crack;

    @Option(
            names = {"-C", "--crack-bulk"},
            description = "Option to crack a list of users, reading from the requested text-file within this local directory." +
                    " If this command is used, the --username and --crack option is not required"
    )
    boolean bulk;

    @Option(
            names = {"-p", "--passwords"},
            required = true,
            description = "Path where your passwords file is stored, data is only read from this file"
    )
    public  File passwordsFile;


    /**
     *
     */
    @Option(
            names = {"-U", "--username-bulk-file"},
            description = "Path where your list of usernames file is stored, data is only read from this file"
    )
    public File usernamesFile;

    /**
     * Option -o / --outfile is REQUIRED within the flags
     * This is the file that the cracked accounts are outputted to
     */
    @Option(
            names = {"-o", "--outfile"},
            required = true,
            description =
            "Path where your outfile is stored, data is written and appended to this file. Successful cracked accounts will be stored in this file"
    )
    public File outfileFile;

    @Spec CommandSpec spec;

    /**
     * Creates Main Thread
     * @param args
     */

    public static void main(String[] args) {
        new CommandLine(new Main()).execute(args);
    }

    private static final String[] CRACK_REQUIREMENTS = {"username", "passwordsFile", "outfileFile"};
    private static final String[] CRACK_BULK_REQUIREMENTS = {"usernamesFile", "passwordsFile", "outfileFile"};

    /**
     * Validates if the required fields within the @aniski.Main class file is not null
     * otherwise throw new ParamaterException is called
     * Throws the following errors if the fields do not exist within the classFile
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */

    private void validateFlagRequirements() throws NoSuchFieldException, IllegalAccessException {
        FlagManager flagManager = new FlagManager();
        if(crack && bulk) {
            throw new ParameterException(spec.commandLine(), "You can't use --crack and --crack-bulk at the same time");
        } else if(crack) {
            for(String requirement : CRACK_REQUIREMENTS) {
                Field field = this.getClass().getField(requirement);
                if(field.get(this) == null) {
                    throw new ParameterException(spec.commandLine(), "--" + requirement + " flag is required when using --crack");
                }
            }
            Object[] flagArgs = new Object[]{"crack", passwordsFile, outfileFile, username};
            flagManager.executeFlag(flagArgs);
        } else if(bulk) {
            for(String requirement : CRACK_BULK_REQUIREMENTS) {
                Field field = this.getClass().getField(requirement);
                if(field.get(this) == null) {
                    throw new ParameterException(spec.commandLine(), "--" + requirement + " flag is required when using --crack");
                }
            }
            Object[] flagArgs = new Object[]{"crack-bulk", passwordsFile, outfileFile, usernamesFile};
            flagManager.executeFlag(flagArgs);
        } else {
            throw new ParameterException(spec.commandLine(), "You have to include --crack or --crack-bulk within the flags");
        }
        System.out.println("["+this.getClass().getName()+"] END OF validateFlagRequirements() void");
    }

    /**
     * After Main Thread is ran, run() is called and tries to call validateFlagRequirements() void
     * Throws the following errors if the fields do not exist within the classFile
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */

    @Override
    public void run() {
        //No other commands right now so don't accept any commands that are not including -r / --rsps
        if (!rsps)
            return;
        try {
            validateFlagRequirements();
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            System.out.println("Error starting Aniski Console.");
            //e.printStackTrace();
        }
        CommandLine.usage(this, System.out);
    }
}