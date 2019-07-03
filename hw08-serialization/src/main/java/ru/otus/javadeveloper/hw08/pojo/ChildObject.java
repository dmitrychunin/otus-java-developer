package ru.otus.javadeveloper.hw08.pojo;

import lombok.Value;

import java.util.Arrays;
import java.util.Collection;

@Value
public class ChildObject {
    private boolean flag1;
    private boolean flag2;
    private int int1;
    private String str1;

    private String nullS = null;
    private char c = 'a';
    private int i = 1;
    private byte b = 1;
    private short s = 1;
    private long l = 1;
    private float f = 1;
    private double d = 1;
    private Integer ei = 1;
    private Byte eb = 1;
    private Short es = 1;
    private Long el = 1L;
    private Float ef = 1f;
    private Double ed = 1d;

    private String[] strArray = {"str1", "str2"};
    private char[] cArray = {'a', 'a'};
    private int[] iArray = {1, 1};
    private byte[] bArray = {1, 1};
    private short[] sArray = {1, 1};
    private long[] lArray = {1, 1};
    private float[] fArray = {1, 1};
    private double[] dArray = {1, 1};
    private Integer[] eiArray = {1, 1};
    private Byte[] ebArray = {1, 1};
    private Short[] esArray = {1, 1};
    private Long[] elArray = {1L, 1L};
    private Float[] efArray = {1f, 1f};
    private Double[] edArray = {1d, 1d};


    private Collection<String> strCollection = Arrays.asList("str1", "str2");
    private Collection<Integer> eiCollection = Arrays.asList(1, 1);
    private Collection<Byte> ebCollection = Arrays.asList((byte) 1, (byte) 1);
    private Collection<Short> esCollection = Arrays.asList((short) 1, (short) 1);
    private Collection<Long> elCollection = Arrays.asList(1L, 1L);
    private Collection<Float> efCollection = Arrays.asList(1f, 1f);
    private Collection<Double> edCollection = Arrays.asList(1d, 1d);
}
