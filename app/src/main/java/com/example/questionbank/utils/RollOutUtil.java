package com.example.questionbank.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 题库题号索引生成器
 */
public class RollOutUtil {
    public static final String  ALL_RANDOM = "allRandom";
    public static final String JUST_WRONG = "justWrong";
    public static final String NO_EZ = "noEasy";
    //String JUST_NEW = "new";
    public static int[] rollOut(String type,int cNum,int jNUm){
        switch (type){
            case ALL_RANDOM:
                return allRandom(cNum,jNUm);
            case JUST_WRONG:
                break;
            case NO_EZ:

                break;
        }
        return null;
    }



    private static int[]  allRandom(int cNum,int jNum){
        int[] cArray = random(1200,cNum);
        int[] jArray = random(1201,2000,jNum);
        //System.arraycopy(cArray, 0, jArray, 0, cArray.length);
        int[] result = Arrays.copyOf(cArray,cNum + jNum);
        for(int i = cNum; i < cNum + jNum;i++){
            result[i] = jArray[i-cNum];
        }
        return result;
    }

    private static int[] random(int bound,int need){
        Random random = new Random();
        //int[] startArray = new int[1200];
        List<Integer> numbers = new ArrayList<>();
        for(int i = 1; i <= bound ; i ++){
            numbers.add(i);
        }
        int cArray[]  = new int[need];
        for(int i = 0; i < need; i++){
            int index = random.nextInt(bound -i);
            cArray[i] = numbers.remove(index);
        }
        return cArray;
    }

    private static int[] random(int bound1,int bound2,int need){
        Random random = new Random();
        //int[] startArray = new int[1200];
        List<Integer> numbers = new ArrayList<>();
        for(int i = bound1; i <= bound2 ; i ++){
            numbers.add(i);
        }
        int cArray[]  = new int[need];
        int bounds = bound2 - bound1 + 1;
        for(int i = 0; i < need; i++){
            int index = random.nextInt(bounds -i);
            cArray[i] = numbers.remove(index);
            //startArray[index] = startArray[index-1];
        }
        return cArray;
    }

    /**
     * 设计失误导致的方法冗余
     * 随机产生错误题目的position
     * 取的时候下标还要记得-1，不然会越界异常
     * @param cNum
     * @param jNUm
     * @return
     */
    public static List<int[]> rollOutWrong(int cNum,int jNUm,int cBound, int jBound){
        return allRandom(cNum,jNUm,cBound,jBound);
    }

    private static List<int[]>  allRandom(int cNum,int jNum , int cBound, int jBound){
        int[] cArray = random(cBound,cNum);
        int[] jArray = random(jBound,jNum);
        //System.arraycopy(cArray, 0, jArray, 0, cArray.length);
//        int[] result = Arrays.copyOf(cArray,cNum + jNum);
//        for(int i = cNum; i < cNum + jNum;i++){
//            result[i] = jArray[i-cNum];
//        }
        List<int[]> ints = new ArrayList<>(2);
        ints.add(cArray);
        ints.add(jArray);
        return ints;
    }

    public static void main(String[] args) {
        for(int[] i : rollOutWrong(2,4,10,4)){
            System.out.println(Arrays.toString(i));
        }
    }
}
