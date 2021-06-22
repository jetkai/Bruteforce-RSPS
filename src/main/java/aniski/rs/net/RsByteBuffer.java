package aniski.rs.net;
/* aniski.rs.net.onyx.RsByteBuffer - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.math.BigInteger;

public class RsByteBuffer extends Linkable {
	public byte[] buffer;
	public int currentOffset;
	static int[] anIntArray7614 = new int[256];
	static int anInt7615 = -306674912;
	public static int anInt7616 = 5000;
	static long aLong7617 = -3932672073523589310L;
	public static int anInt7618 = 100;
	public static long[] aLongArray7619;

	public void method3584(long l) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) (l >> 32);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) (l >> 24);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) (l >> 16);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) (l >> 8);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) l;
	}

	public void writeByte(int i) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) i;
	}

	public void writeShort(int i, int i_1_) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 8);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) i;
	}

	public void method3587(int i, int i_2_) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) i;
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 8);
	}

	public void writeInt(int i, int i_3_) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 24);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 16);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 8);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) i;
	}

	public void writeLEInt(int i, int i_4_) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) i;
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 8);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 16);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 24);
	}

	public void writeString(String string, int i) {
			int i_5_ = string.indexOf('\0');
			if (i_5_ >= 0)
				throw new IllegalArgumentException("");
			currentOffset += Class361.method4304(string, 0, string.length(), buffer, 385051775 * currentOffset, (byte) 41) * 116413311;
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) 0;
	}

	public int readShort(int i) {
			currentOffset += 232826622;
			int i_6_ = (((buffer[currentOffset * 385051775 - 2] & 0xff) << 8) + (buffer[385051775 * currentOffset - 1] & 0xff));
			if (i_6_ > 32767)
				i_6_ -= 65536;
			return i_6_;
	}

	public void writeIntV1(int i, byte i_7_) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 8);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) i;
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 24);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 16);
	}

	public void method3593(int i, int i_8_) {
			buffer[385051775 * currentOffset - i - 2] = (byte) (i >> 8);
			buffer[385051775 * currentOffset - i - 1] = (byte) i;
	}

	public void method3594(int i, byte i_9_) {
			if (i >= 0 && i < 128)
				writeByte(i);
			else if (i >= 0 && i < 32768)
				writeShort(32768 + i, 16711935);
			else
				throw new IllegalArgumentException();
	}

	public int readUnsignedByte() {
			return (buffer[(currentOffset += 116413311) * 385051775 - 1] & 0xff);
	}

	public byte readByte(int i) {
		return buffer[(currentOffset += 116413311) * 385051775 - 1];
	}

	final int readCustomUnsignedShort() {
		int v = readUnsignedShort();
		if (v == 65535)
			return -1;
		return v;
	}

	public int readUnsignedShort() {
			currentOffset += 232826622;
			return ((buffer[currentOffset * 385051775 - 1] & 0xff) + ((buffer[currentOffset * 385051775 - 2] & 0xff) << 8));
	}

	public long readLong(short i) {
			long l = (long) readUnsignedInt((byte) 35) & 0xffffffffL;
			long l_10_ = (long) readUnsignedInt((byte) 13) & 0xffffffffL;
			return (l << 32) + l_10_;
	}

	public void method3599(int i, int i_11_) {
			buffer[385051775 * currentOffset - i - 4] = (byte) (i >> 24);
			buffer[currentOffset * 385051775 - i - 3] = (byte) (i >> 16);
			buffer[385051775 * currentOffset - i - 2] = (byte) (i >> 8);
			buffer[385051775 * currentOffset - i - 1] = (byte) i;
	}

	public int method3600(int i) {
			currentOffset += 465653244;
			return (((buffer[currentOffset * 385051775 - 1] & 0xff) << 24) + ((buffer[currentOffset * 385051775 - 2] & 0xff) << 16) + ((buffer[currentOffset * 385051775 - 3] & 0xff) << 8) + (buffer[385051775 * currentOffset - 4] & 0xff));
	}

	public long method3601(byte i) {
			long l = (long) readUnsignedByte() & 0xffffffffL;
			long l_12_ = (long) readUnsignedInt((byte) 19) & 0xffffffffL;
			return (l << 32) + l_12_;
	}

/*	public String readString(int i) {
			int i_13_ = 385051775 * offset;
			while (buffer[(offset += 116413311) * 385051775 - 1] != 0) {
				if (i == 968466925) {
					*//* empty *//*
				}
			}
			int i_14_ = offset * 385051775 - i_13_ - 1;
			if (i_14_ == 0)
				return "";
			return Class52.method556(buffer, i_13_, i_14_, 964250329);
	}*/

	public void method3603(long l, int i, int i_15_) {
			if (--i < 0 || i > 7)
				throw new IllegalArgumentException();
			for (int i_16_ = i * 8; i_16_ >= 0; i_16_ -= 8)
				buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) (l >> i_16_);
	}

	public void readBytes(byte[] is, int i, int i_17_, int i_18_) {
			for (int i_19_ = i; i_19_ < i + i_17_; i_19_++)
				is[i_19_] = buffer[(currentOffset += 116413311) * 385051775 - 1];
	}

	public int readUnsignedSmart(int i) {
		int i_20_ = buffer[currentOffset * 385051775] & 0xff;
		if (i_20_ < 128)
			return readUnsignedByte();
		return readUnsignedShort() - 32768;
	}

	public int readUnsignedSmartNS() {
		int i_94_ = buffer[currentOffset * 385051775] & 0xff;
		if (i_94_ < 128) {
			return readUnsignedByte() - 1;
		}
		return readUnsignedShort() - 32769;
	}

	public int readSmart(short i) {
		int i_21_ = 0;
		int i_22_;
		for (i_22_ = readUnsignedSmart(1723054621); 32767 == i_22_; i_22_ = readUnsignedSmart(1723054621))
			i_21_ += 32767;
		i_21_ += i_22_;
		return i_21_;
	}

	public int method3607(int i) {
			if (buffer[currentOffset * 385051775] < 0)
				return readUnsignedInt((byte) 47) & 0x7fffffff;
			return readUnsignedShort();
	}


	public void writeLong(long l) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) (l >> 56);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) (l >> 48);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) (l >> 40);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) (l >> 32);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) (l >> 24);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) (l >> 16);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) (l >> 8);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (int) l;
	}

	public int method3609(int i) {
			int i_23_ = buffer[(currentOffset += 116413311) * 385051775 - 1];
			int i_24_ = 0;
			for (/**/; i_23_ < 0; i_23_ = (buffer[(currentOffset += 116413311) * 385051775 - 1]))
				i_24_ = (i_24_ | i_23_ & 0x7f) << 7;
			return i_24_ | i_23_;
	}

	public void method3610(int[] is, int i) {
			int i_25_ = 385051775 * currentOffset / 8;
			currentOffset = 0;
			for (int i_26_ = 0; i_26_ < i_25_; i_26_++) {
				int i_27_ = readUnsignedInt((byte) -31);
				int i_28_ = readUnsignedInt((byte) -90);
				int i_29_ = -957401312;
				int i_30_ = -1640531527;
				int i_31_ = 32;
				while (i_31_-- > 0) {
					i_28_ -= (i_27_ + (i_27_ << 4 ^ i_27_ >>> 5) ^ is[i_29_ >>> 11 & 0x3] + i_29_);
					i_29_ -= i_30_;
					i_27_ -= (i_28_ + (i_28_ << 4 ^ i_28_ >>> 5) ^ i_29_ + is[i_29_ & 0x3]);
				}
				currentOffset -= 931306488;
				writeInt(i_27_, -429646008);
				writeInt(i_28_, 96557392);
			}
	}

	public void method3611(int[] is, int i, int i_32_, int i_33_) {
			int i_34_ = 385051775 * currentOffset;
			currentOffset = i * 116413311;
			int i_35_ = (i_32_ - i) / 8;
			for (int i_36_ = 0; i_36_ < i_35_; i_36_++) {
				int i_37_ = readUnsignedInt((byte) -6);
				int i_38_ = readUnsignedInt((byte) -16);
				int i_39_ = 0;
				int i_40_ = -1640531527;
				int i_41_ = 32;
				while (i_41_-- > 0) {
					i_37_ += ((i_38_ << 4 ^ i_38_ >>> 5) + i_38_ ^ is[i_39_ & 0x3] + i_39_);
					i_39_ += i_40_;
					i_38_ += ((i_37_ << 4 ^ i_37_ >>> 5) + i_37_ ^ i_39_ + is[i_39_ >>> 11 & 0x3]);
				}
				currentOffset -= 931306488;
				writeInt(i_37_, -1455722924);
				writeInt(i_38_, -1798688670);
			}
			currentOffset = 116413311 * i_34_;
	}

	public void method3612(int[] is, int i, int i_42_, int i_43_) {
			int i_44_ = currentOffset * 385051775;
			currentOffset = i * 116413311;
			int i_45_ = (i_42_ - i) / 8;
			for (int i_46_ = 0; i_46_ < i_45_; i_46_++) {
				int i_47_ = readUnsignedInt((byte) -22);
				int i_48_ = readUnsignedInt((byte) -60);
				int i_49_ = -957401312;
				int i_50_ = -1640531527;
				int i_51_ = 32;
				while (i_51_-- > 0) {
					i_48_ -= (i_47_ + (i_47_ << 4 ^ i_47_ >>> 5) ^ i_49_ + is[i_49_ >>> 11 & 0x3]);
					i_49_ -= i_50_;
					i_47_ -= (i_48_ + (i_48_ << 4 ^ i_48_ >>> 5) ^ is[i_49_ & 0x3] + i_49_);
				}
				currentOffset -= 931306488;
				writeInt(i_47_, -1451619282);
				writeInt(i_48_, -1662476613);
			}
			currentOffset = i_44_ * 116413311;
	}

	public int read24BitUnsignedInteger(byte i) {
		currentOffset += 349239933;
		return ((buffer[385051775 * currentOffset - 1] & 0xff) + (((buffer[currentOffset * 385051775 - 3] & 0xff) << 16) + ((buffer[385051775 * currentOffset - 2] & 0xff) << 8)));

	}

/*	public int method3614(int i, int i_52_) {
			int i_53_ = Class11.method328(buffer, i, offset * 385051775, -1501053505);
			writeInt(i_53_, 533083974);
			return i_53_;
	}

	public boolean method3615(byte i) {
			offset -= 465653244;
			int i_54_ = Class11.method328(buffer, 0, 385051775 * offset, -395934040);
			int i_55_ = readUnsignedInt((byte) -63);
			if (i_55_ == i_54_)
				return true;
			return false;
	}*/

	public void writeByte128(int i, int i_56_) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (128 + i);
	}

	public void method3617(int i, int i_57_) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (0 - i);
	}

	public int readUnsignedByte128(int i) {
			return ((buffer[(currentOffset += 116413311) * 385051775 - 1] - 128) & 0xff);
	}

	public int readUnsignedByteC(short i) {
			return (0 - (buffer[(currentOffset += 116413311) * 385051775 - 1]) & 0xff);
	}

	public int readUnsigned128Byte(byte i) {
			return (128 - (buffer[(currentOffset += 116413311) * 385051775 - 1]) & 0xff);
	}

	public byte readByteC(int i) {
			return (byte) (0 - (buffer[(currentOffset += 116413311) * 385051775 - 1]));
	}

	public byte read128Byte(int i) {
			return (byte) (128 - (buffer[(currentOffset += 116413311) * 385051775 - 1]));
	}

	public void writeShortLE128(int i) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i + 128);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 8);
	}

	public int readUnsignedShortLE(byte i) {
			currentOffset += 232826622;
			return ((buffer[385051775 * currentOffset - 2] & 0xff) + ((buffer[currentOffset * 385051775 - 1] & 0xff) << 8));
	}

	public int readUnsignedShort128(int i) {
			currentOffset += 232826622;
			return (((buffer[385051775 * currentOffset - 2] & 0xff) << 8) + (buffer[currentOffset * 385051775 - 1] - 128 & 0xff));
	}

	public int readUnsignedShortLE128(int i) {
			currentOffset += 232826622;
			return (((buffer[385051775 * currentOffset - 1] & 0xff) << 8) + (buffer[385051775 * currentOffset - 2] - 128 & 0xff));
	}

	public int read24BitInteger(byte i) {
			currentOffset += 349239933;
			int i_59_ = (((buffer[currentOffset * 385051775 - 3] & 0xff) << 16) + ((buffer[currentOffset * 385051775 - 2] & 0xff) << 8) + (buffer[385051775 * currentOffset - 1] & 0xff));
			if (i_59_ > 8388607)
				i_59_ -= 16777216;
			return i_59_;
	}

	public int read24BitUnsignedIntegerV2(byte i) {
			currentOffset += 349239933;
			return ((buffer[385051775 * currentOffset - 2] & 0xff) + (((buffer[currentOffset * 385051775 - 3] & 0xff) << 16) + ((buffer[385051775 * currentOffset - 1] & 0xff) << 8)));
	}

	public void writeIntLE(int i, byte i_60_) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) i;
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 8);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 16);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 24);
	}

	public byte readByte128(byte i) {
			return (byte) ((buffer[(currentOffset += 116413311) * 385051775 - 1]) - 128);
	}

	public void writeIntV2(int i) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 16);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 24);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) i;
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 8);
	}

	public int readIntLE(int i) {
			currentOffset += 465653244;
			return (((buffer[currentOffset * 385051775 - 3] & 0xff) << 8) + (((buffer[currentOffset * 385051775 - 1] & 0xff) << 24) + ((buffer[currentOffset * 385051775 - 2] & 0xff) << 16)) + (buffer[currentOffset * 385051775 - 4] & 0xff));
	}

	public int readIntV2(byte i) {
			currentOffset += 465653244;
			return ((buffer[385051775 * currentOffset - 2] & 0xff) + (((buffer[currentOffset * 385051775 - 1] & 0xff) << 8) + (((buffer[currentOffset * 385051775 - 4] & 0xff) << 16) + ((buffer[385051775 * currentOffset - 3] & 0xff) << 24))));
	}

	static {
		for (int i = 0; i < 256; i++) {
			int i_62_ = i;
			for (int i_63_ = 0; i_63_ < 8; i_63_++) {
				if ((i_62_ & 0x1) == 1)
					i_62_ = i_62_ >>> 1 ^ ~0x12477cdf;
				else
					i_62_ >>>= 1;
			}
			anIntArray7614[i] = i_62_;
		}
		aLongArray7619 = new long[256];
		for (int i = 0; i < 256; i++) {
			long l = i;
			for (int i_64_ = 0; i_64_ < 8; i_64_++) {
				if ((l & 0x1L) == 1L)
					l = l >>> 1 ^ ~0x3693a86a2878f0bdL;
				else
					l >>>= 1;
			}
			aLongArray7619[i] = l;
		}
	}

	public void putTriByte(int i, byte i_65_) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 16);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 8);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) i;
	}

	public void applyRsa(BigInteger biginteger, BigInteger biginteger_66_, int i) {
			int i_67_ = currentOffset * 385051775;
			currentOffset = 0;
			byte[] is = new byte[i_67_];
			readBytes(is, 0, i_67_, -953523806);
			BigInteger biginteger_68_ = new BigInteger(is);
			System.out.println("BigIntegerOutPut: "+biginteger+", "+biginteger_66_);
			boolean disableRSA = false;
			BigInteger biginteger_69_ = disableRSA ? biginteger_68_ : biginteger_68_.modPow(biginteger, biginteger_66_);
			byte[] is_70_ = biginteger_69_.toByteArray();
			currentOffset = 0;
			writeShort(is_70_.length, 16711935);
			writeBytes(is_70_, 0, is_70_.length, (short) -25461);
	}

	public RsByteBuffer(int i) {
		buffer = Class416.method5589(i, (short) -31789);
		currentOffset = 0;
	}

	/*public String readJagString(int i) {
			byte i_71_ = buffer[(offset += 116413311) * 385051775 - 1];
			if (0 != i_71_)
				throw new IllegalStateException("");
			int i_72_ = offset * 385051775;
			while_13_: do {
				do {
					if ((buffer[(offset += 116413311) * 385051775 - 1]) == 0)
						break while_13_;
				}
				while (i == 681479919);
				throw new IllegalStateException();
			}
			while (false);
			int i_73_ = offset * 385051775 - i_72_ - 1;
			if (0 == i_73_)
				return "";
			return Class52.method556(buffer, i_72_, i_73_, -1673599026);
	}*/

	public int readIntV1(int i) {
			currentOffset += 465653244;
			return (((buffer[385051775 * currentOffset - 1] & 0xff) << 16) + ((buffer[385051775 * currentOffset - 2] & 0xff) << 24) + ((buffer[currentOffset * 385051775 - 4] & 0xff) << 8) + (buffer[currentOffset * 385051775 - 3] & 0xff));
	}

	public void write128Byte(int i, byte i_74_) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (128 - i);
	}

	public void method3639(int i, int i_75_) {
			if ((i & ~0x7f) != 0) {
				if ((i & ~0x3fff) != 0) {
					if ((i & ~0x1fffff) != 0) {
						if (0 != (i & ~0xfffffff))
							writeByte(i >>> 28 | 0x80);
						writeByte(i >>> 21 | 0x80);
					}
					writeByte(i >>> 14 | 0x80);
				}
				writeByte(i >>> 7 | 0x80);
			}
			writeByte(i & 0x7f);
	}

	public long method3640(int i) {
			long l = (long) method3600(143621107) & 0xffffffffL;
			long l_76_ = (long) method3600(742659427) & 0xffffffffL;
			return (l_76_ << 32) + l;
	}

	/*public void putJagString(String string, short i) {
		try {
			int i_77_ = string.indexOf('\0');
			if (i_77_ >= 0)
				throw new IllegalArgumentException("");
			buffer[(offset += 116413311) * 385051775 - 1] = (byte) 0;
			offset += Class361.method4304(string, 0, string.length(), buffer, 385051775 * offset, (byte) 102) * 116413311;
			buffer[(offset += 116413311) * 385051775 - 1] = (byte) 0;
		}
		catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, "acx.c(" + ')');
		}
	}*/

	public void writeShort128(int i) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 8);
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i + 128);
	}

	public int readUnsignedInt(byte i) {
		currentOffset += 465653244;
		return (((buffer[385051775 * currentOffset - 3] & 0xff) << 16) + ((buffer[385051775 * currentOffset - 4] & 0xff) << 24) + ((buffer[currentOffset * 385051775 - 2] & 0xff) << 8) + (buffer[385051775 * currentOffset - 1] & 0xff));
	}

	public void writeShortLE(int i, int i_79_) {
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) i;
			buffer[(currentOffset += 116413311) * 385051775 - 1] = (byte) (i >> 8);
	}

	public RsByteBuffer(byte[] is) {
		buffer = is;
		currentOffset = 0;
	}

	public int readSmartReal(int i) {
		int i_80_ = buffer[385051775 * currentOffset] & 0xff;
		if (i_80_ < 128)
			return readUnsignedByte() - 64;
		return readUnsignedShort() - 49152;
	}

	public int readBigSmart(int i) {// readBigSmart
		if (buffer[385051775 * currentOffset] < 0)
			return readUnsignedInt((byte) 33) & 0x7fffffff;
		int i_81_ = readUnsignedShort();
		if (32767 == i_81_)
			return -1;
		return i_81_;
	}

	public long method3647(int i, byte i_82_) {
			if (--i < 0 || i > 7)
				throw new IllegalArgumentException();
			int i_83_ = 8 * i;
			long l = 0L;
			for (/**/; i_83_ >= 0; i_83_ -= 8)
				l |= ((long) (buffer[(currentOffset += 116413311) * 385051775 - 1]) & 0xffL) << i_83_;
			return l;
	}

	public void writeBytes(byte[] is, int i, int i_84_, short i_85_) {
			for (int i_86_ = i; i_86_ < i_84_ + i; i_86_++)
				buffer[(currentOffset += 116413311) * 385051775 - 1] = is[i_86_];
	}

	public void method3649(int i, byte i_87_) {
			buffer[currentOffset * 385051775 - i - 1] = (byte) i;
	}

/*	public void method3650(int i) {
			if (null != buffer)
				Class201.method1900(buffer, -860028882);
			buffer = null;
	}*/

	public int method3651(int i) {
		currentOffset += 232826622;
		int i_88_ = ((buffer[currentOffset * 385051775 - 2] & 0xff) + ((buffer[currentOffset * 385051775 - 1] & 0xff) << 8));
		if (i_88_ > 32767)
			i_88_ -= 65536;
		return i_88_;
	}

/*	public String readJNullString(int i) {
			if (buffer[385051775 * offset] == 0) {
				offset += 116413311;
				return null;
			}
			return readString(541883117);
	}*/

/*	static void method3653(Class403 class403, int i) {
			String string = (String) (class403.anObjectArray5240[(class403.anInt5241 -= 969361751) * -203050393]);
			class403.anObjectArray5240[((class403.anInt5241 += 969361751) * -203050393 - 1)] = string.toLowerCase();
	}

	static void method3654(Class403 class403, int i) {
			int i_89_ = (class403.anIntArray5244[((class403.anInt5239 -= -391880689) * 681479919)]);
			IComponentDefinition class105 = Class50.getIComponentDefinitions(i_89_, (byte) 28);
			Class119 class119 = Class389.aClass119Array4165[i_89_ >> 16];
			SeqType.method4887(class105, class119, class403, (byte) -121);
	}

	static void method3655(Class403 class403, int i) {
			class403.anIntArray5244[((class403.anInt5239 += -391880689) * 681479919 - 1)] = (Class422_Sub25.preferences.aClass422_Sub13_7549.method5674(-484902399) && OverlayDefinition.activeToolkit.method5032()) ? 1 : 0;
	}*/

/*	static void method3656(int i, int i_90_, int i_91_, short i_92_) {
			Class298_Sub37_Sub12 class298_sub37_sub12 = Class410.method4985(9, i);
			class298_sub37_sub12.method3449((byte) 105);
			class298_sub37_sub12.type = 1274450087 * i_90_;
			class298_sub37_sub12.value = 293101103 * i_91_;
	}*/

/*	static void method3657(Class403 class403, byte i) {
		try {
			String string = (String) (class403.anObjectArray5240[(class403.anInt5241 -= 969361751) * -203050393]);
			int i_93_ = (class403.anIntArray5244[((class403.anInt5239 -= -391880689) * 681479919)]);
			if (-1 == i_93_)
				throw new RuntimeException("");
			class403.anObjectArray5240[((class403.anInt5241 += 969361751) * -203050393 - 1)] = string + (char) i_93_;
		}
		catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, "acx.za(" + ')');
		}
	}*/

	public float readFloat(int i) {
		return Float.intBitsToFloat(readUnsignedInt((byte) 0));
	}
}
