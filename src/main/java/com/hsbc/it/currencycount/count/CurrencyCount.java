package com.hsbc.it.currencycount.count;

import com.alibaba.fastjson.JSONObject;
import com.hsbc.it.currencycount.vo.CurrencyAmount;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyCount {

    /**
     * Calculates the amount of currency
     *
     * @param list
     * @return
     * @throws Exception
     */
    public Map<String, CurrencyAmount> calculate(List<CurrencyAmount> list) {
        Map<String, CurrencyAmount> map = new HashMap<>();
        JSONObject rateJson = getRate();
        for (CurrencyAmount currencyAmount : list) {
            BigDecimal amountDecimal = currencyAmount.getAmount();
            if (map.containsKey(currencyAmount.getCurrency())) {
                amountDecimal = map.get(currencyAmount.getCurrency()).getAmount().add(currencyAmount.getAmount());
                if(amountDecimal.intValue() == 0){
                    map.remove(currencyAmount.getCurrency());
                    continue;
                }
            }
            if (amountDecimal.intValue() != 0) {
                //count rate
                if(rateJson != null){
                    String rate = rateJson.getString(currencyAmount.getCurrency());
                    BigDecimal rateDecimal = new BigDecimal(rate);
                    BigDecimal usdDecial = amountDecimal.divide(rateDecimal, 2, BigDecimal.ROUND_HALF_UP);
                    currencyAmount.setUsd(usdDecial);
                }
                currencyAmount.setAmount(amountDecimal);
                map.put(currencyAmount.getCurrency(), currencyAmount);
            }
        }
        return map;
    }

    /**
     * call inteface to get rate
     * @return
     */
    public JSONObject getRate(){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        String url = "https://open.exchangerate-api.com/v6/latest";
        try {
            // 创建Get请求
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(2000) //服务器响应超时时间
                    .setConnectTimeout(2000) //连接服务器超时时间
                    .build();
            httpGet.setConfig(requestConfig);
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(responseEntity));
                JSONObject rateJson = jsonObject.getJSONObject("rates");
                return rateJson;
            }

        } catch (Exception e) {
            System.out.println("call interface exception,can not get rate");
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
