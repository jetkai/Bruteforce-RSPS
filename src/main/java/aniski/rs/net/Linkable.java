package aniski.rs.net;/* Class298 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Linkable {
    public Linkable aClass298_3187;
    public long hash;
    public Linkable aClass298_3189;
    public static int anInt3190;
    static int anInt3191;
    static int anInt3192;

    public void unlink(int i) {
	    if (aClass298_3189 != null) {
		aClass298_3189.aClass298_3187 = aClass298_3187;
		aClass298_3187.aClass298_3189 = aClass298_3189;
		aClass298_3187 = null;
		aClass298_3189 = null;
	    }
    }

    public boolean method2840(int i) {
	    if (null == aClass298_3189)
		return false;
	    return true;
    }
}
