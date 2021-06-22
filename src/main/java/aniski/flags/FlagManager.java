package aniski.flags;

import aniski.flags.impl.Crack;
import aniski.flags.impl.CrackBulk;

import java.util.HashMap;
import java.util.Map;

public class FlagManager {

    private static final Map<Object, Flag> FLAGS = new HashMap<>();

    public FlagManager() {
        FLAGS.put("crack", new Crack());
        FLAGS.put("crack-bulk", new CrackBulk());
    }

    public void executeFlag(Object[] f) {
        Flag flag = FLAGS.get(f[0]);
        if(flag != null)
            flag.init(f);
    }

}
