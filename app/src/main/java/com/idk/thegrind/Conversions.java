package com.idk.thegrind;

public class Conversions {
    public static String convertArrayToString(int array[]) {
        StringBuilder arrayString = new StringBuilder((String) "");

        for(int i = 0; i<array.length; i++){
            if(i != 0){
                arrayString.append("@");
            }
            if(array[i]<0){
                arrayString.append("-1");
            } else {
                arrayString.append(array[i]);
            }
        }

        return arrayString.toString();
    }

    public static String convertArrayToStringTime(String array[]) {
        StringBuilder arrayString = new StringBuilder((String) "");

        for(int i = 0; i<array.length; i++){
            if(i != 0){
                arrayString.append("@");
            }
            if(Long.parseLong(array[i])<0){
                arrayString.append("-1");
            } else {
                arrayString.append(array[i]);
            }
        }

        return arrayString.toString();
    }

    public static String[] convertArrayStringToArray(String arrayString){
        String[] newArray = arrayString.split("@", -1);

        return newArray;
    }

    public static int[] convertArrayStringToArrayInt(String arrayString){
        String[] newArray = arrayString.split("@", -1);
        int[] newIntArray = new int[newArray.length];

        for(int i = 0; i<newArray.length; i++){
            newIntArray[i] = Integer.parseInt(newArray[i]);
        }

        return newIntArray;
    }
}
