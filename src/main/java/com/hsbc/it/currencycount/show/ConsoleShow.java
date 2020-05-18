package com.hsbc.it.currencycount.show;

import com.hsbc.it.currencycount.count.CurrencyCount;
import com.hsbc.it.currencycount.utils.CheckUtil;
import com.hsbc.it.currencycount.utils.FileLoadUtil;
import com.hsbc.it.currencycount.vo.CurrencyAmount;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleShow {

    /**
     * 打印控制台
     */
    public void printConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("would you like to typed into cammand line or load from file?\n" +
                "A:typed into cammand line\n" +
                "B:load from file");
        String str = scanner.nextLine();
        if ("A".equalsIgnoreCase(str)) {
            System.out.println("please typed into cammand line.when you finish, press 'a' first ,and then press 'enter'");
            int lineNumber = 1;
            List<CurrencyAmount> list = new ArrayList<>();

            while (scanner.hasNextLine()) {
                str = scanner.nextLine();
                if ("a".equalsIgnoreCase(str)) {
                    System.out.println("counting...");
                    break;
                }
                if(str.trim().length() != 0){
                    CurrencyAmount currencyAmount = CheckUtil.inputCheck(str, lineNumber);
                    lineNumber++;
                    if (currencyAmount != null) {
                        list.add(currencyAmount);
                    } else {
                        System.exit(0);
                    }
                }

            }

            CurrencyCount currencyCount = new CurrencyCount();
            Map<String, CurrencyAmount> map = currencyCount.calculate(list);
            formatOut(map, scanner);

        } else if ("B".equalsIgnoreCase(str)) {
            System.out.println("please enter the filepath,and then press 'enter'");
            str = scanner.nextLine();
            System.out.println("counting...");
            try {
                List<CurrencyAmount> list = FileLoadUtil.readFile(str);
                if (list == null) {
                    System.exit(0);
                } else {
                    CurrencyCount currencyCount = new CurrencyCount();
                    Map<String, CurrencyAmount> map = currencyCount.calculate(list);
                    formatOut(map, scanner);
                }
            } catch (Exception e) {
                System.out.println("unkown system exception!");
                System.exit(0);
            }
        } else {
            System.out.println("error input!!Bye Bye");
            System.exit(0);
        }

    }

    /**
     * format Output
     *
     * @param map
     */
    public void formatOut(Map<String, CurrencyAmount> map, Scanner scanner) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                for (Map.Entry<String, CurrencyAmount> entry : map.entrySet()) {
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(2);
                    nf.setMinimumFractionDigits(2);
                    nf.setRoundingMode(RoundingMode.HALF_UP);
                    nf.setGroupingUsed(false);
                    String value = nf.format(entry.getValue().getAmount().doubleValue());
                    String usd = "";
                    if(entry.getValue().getUsd() != null){
                        usd = nf.format(entry.getValue().getUsd().doubleValue());
                    }

                    if("USD".equals(entry.getKey())){
                        System.out.println(entry.getKey() + " " + value +"\n");
                    }else {
                        System.out.println(entry.getKey() + " " + value + "(USD "+usd+")\n");
                    }

                    }
                    try {
                        Thread.sleep(60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        while (true) {
            String str = scanner.nextLine();
            if ("quit".equalsIgnoreCase(str)) {
                System.out.println("Bye Bye!");
                System.exit(0);
            }
        }
    }
}
