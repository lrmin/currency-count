package com.hsbc.it.currencycount;

import com.hsbc.it.currencycount.count.CurrencyCount;
import com.hsbc.it.currencycount.vo.CurrencyAmount;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@SpringBootTest
@RunWith(SpringRunner.class)
class CurrencyCountApplicationTests {

    @Test
    void testCalculate() {
        CurrencyAmount currencyAmount = new CurrencyAmount("USD",new BigDecimal("1000"));
        CurrencyAmount currencyAmount2 = new CurrencyAmount("HKD",new BigDecimal("100"));
        CurrencyAmount currencyAmount3 = new CurrencyAmount("USD",new BigDecimal("-100"));
        CurrencyAmount currencyAmount4 = new CurrencyAmount("CNY",new BigDecimal("2000"));
        CurrencyAmount currencyAmount5 = new CurrencyAmount("HKD",new BigDecimal("-100"));

        List<CurrencyAmount> list = new ArrayList<>();
        list.add(currencyAmount);
        list.add(currencyAmount2);
        list.add(currencyAmount3);
        list.add(currencyAmount4);
        list.add(currencyAmount5);

        CurrencyCount count = new CurrencyCount();
        Map<String,CurrencyAmount> map = count.calculate(list);
        Assert.assertEquals("incorrect",900,map.get("USD").getAmount().intValue());
        Assert.assertEquals("incorrect",null,map.get("HKD"));
        Assert.assertEquals("incorrect",2000,map.get("CNY").getAmount().intValue());

    }

}
