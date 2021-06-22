package aniski.rs.net;/* Class416 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class416 {
	static byte[][] aByteArrayArray5327;
	static int anInt5328;
	static int anInt5329;
	static int anInt5330 = 0;
	public static int[] anIntArray5331;
	static byte[][] aByteArrayArray5332;
	static byte[][] aByteArrayArray5333;
	public static int[] anIntArray6388;

	public static synchronized byte[] method5589(int i, short i_0_) {
		if (100 == i && anInt5330 * 355907107 > 0) {
			byte[] is = (aByteArrayArray5332[(anInt5330 -= -996497013) * 355907107]);
			aByteArrayArray5332[anInt5330 * 355907107] = null;
			return is;
		}
		if (i == 5000 && 1904890379 * anInt5328 > 0) {
			byte[] is = (aByteArrayArray5327[(anInt5328 -= -1583470173) * 1904890379]);
			aByteArrayArray5327[1904890379 * anInt5328] = null;
			return is;
		}
		if (i == 30000 && anInt5329 * -1426745913 > 0) {
			byte[] is = (aByteArrayArray5333[(anInt5329 -= 1975229431) * -1426745913]);
			aByteArrayArray5333[anInt5329 * -1426745913] = null;
			return is;
		}
		if (Class298_Sub37_Sub9_Sub2.aByteArrayArrayArray10001 != null) {
			for (int i_1_ = 0; i_1_ < anIntArray6388.length; i_1_++) {
				if (anIntArray6388[i_1_] == i && anIntArray5331[i_1_] > 0) {
					byte[] is = (Class298_Sub37_Sub9_Sub2.aByteArrayArrayArray10001[i_1_][--anIntArray5331[i_1_]]);
					Class298_Sub37_Sub9_Sub2.aByteArrayArrayArray10001[i_1_][anIntArray5331[i_1_]] = null;
					return is;
				}
			}
		}
		return new byte[i];
	}

	Class416() throws Throwable {
		throw new Error();
	}

	static {
		anInt5328 = 0;
		anInt5329 = 0;
		aByteArrayArray5332 = new byte[1000][];
		aByteArrayArray5327 = new byte[250][];
		aByteArrayArray5333 = new byte[50][];
	}

	public static int method5590(int i, byte i_2_) {
		i = --i | i >>> 1;
		i |= i >>> 2;
		i |= i >>> 4;
		i |= i >>> 8;
		i |= i >>> 16;
		return 1 + i;
	}
}

