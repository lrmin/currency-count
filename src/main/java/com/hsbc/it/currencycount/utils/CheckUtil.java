package com.hsbc.it.currencycount.utils;

import com.hsbc.it.currencycount.vo.CurrencyAmount;

import java.math.BigDecimal;

public class CheckUtil {

    /**
     * check input
     * @param str
     * @param lineNumber
     * @return
     */
    public static CurrencyAmount inputCheck(String str,int lineNumber){
        try{
            String []arr = str.split(" ");
            if(arr[0].length() != 3){
                System.out.println("the currency format of line "+lineNumber+ " is not correct ,it's length must be 3");
                return null;
            }
            for(int i=0;i<arr[0].length();i++){
                if(!Character.isUpperCase(arr[0].charAt(i))){
                    System.out.println("the currency format of line "+lineNumber+ " is not correct ,it must be any uppercase letter code");
                    return null;
                }
            }
            CurrencyAmount currencyAmount = new CurrencyAmount(arr[0],new BigDecimal(arr[1]));
            return currencyAmount;
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("the format of line "+lineNumber+" is not correct,please split it by one space!");
            return null;
        }catch (NumberFormatException e){
            System.out.println("the format of line "+lineNumber+" is not correct,please make sure the amount is a number!");
            return null;
        }
    }
}
