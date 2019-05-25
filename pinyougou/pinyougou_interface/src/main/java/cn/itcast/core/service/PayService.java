package cn.itcast.core.service;

import java.util.Map;

/**
 * @author NiYueYeee
 * @create 2019-05-25 17:59
 */
public interface PayService {
    Map<String, String> createNative(String name);

    Map<String, String> queryPayStatus(String out_trade_no);
}
