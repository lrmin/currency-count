package com.hsbc.it.currencycount.utils;

import com.hsbc.it.currencycount.vo.CurrencyAmount;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FileLoadUtil {

    /**
     * read File to List<CurrencyAmount>
     * @param filePath 文件路径
     * @return
     * @throws Exception
     */
    public static List<CurrencyAmount> readFile(String filePath)throws Exception{
        File file=new File(filePath);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("file not found，please make sure the filepath is correct!");
            return null;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String str ;
        int lineNumber = 1;
        List<CurrencyAmount> list = new ArrayList<>();
        while ((str = bufferedReader.readLine()) != null){
            CurrencyAmount currencyAmount = CheckUtil.inputCheck(str,lineNumber);
            if(currencyAmount != null){
                list.add(currencyAmount);
            }
            lineNumber++;
        }
        bufferedReader.close();
        return list;
    }
}
