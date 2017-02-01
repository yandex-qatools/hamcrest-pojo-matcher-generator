package ru.yandex.qatools.proc.test;

import ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher;

/**
 * @author Vladislav Bauer
 */

@SuppressWarnings("unused")
@GenerateMatcher
public class Mob {

    private byte valByte;
    private byte[] valByteArr;
    private Byte valueByte;

    private char valChar;
    private Character valueCharacter;

    private int valInt;
    private Integer valueInteger;

    private long valLong;
    private Long valueLong;

    private float valFloat;
    private Float valueFloat;

    private double valDouble;
    private Double valueDouble;

    private boolean valBoolean;
    private Boolean valueBoolean;


    public byte getValByte() {
        return valByte;
    }

    public Byte getValueByte() {
        return valueByte;
    }

    public char getValChar() {
        return valChar;
    }

    public Character getValueCharacter() {
        return valueCharacter;
    }

    public int getValInt() {
        return valInt;
    }

    public Integer getValueInteger() {
        return valueInteger;
    }

    public long getValLong() {
        return valLong;
    }

    public Long getValueLong() {
        return valueLong;
    }

    public float getValFloat() {
        return valFloat;
    }

    public Float getValueFloat() {
        return valueFloat;
    }

    public double getValDouble() {
        return valDouble;
    }

    public Double getValueDouble() {
        return valueDouble;
    }

    public boolean getValBoolean() {
        return valBoolean;
    }

    public Boolean getValueBoolean() {
        return valueBoolean;
    }

    public byte[] getValByteArr() {
        return valByteArr;
    }
}

