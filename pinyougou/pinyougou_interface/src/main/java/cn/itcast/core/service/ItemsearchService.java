package cn.itcast.core.service;

import java.util.Map;

/**
 * @author NiYueYeee
 * @create 2019-05-12 15:07
 */
public interface ItemsearchService {
    Map<String, Object> search(Map<String, String> searchMap);
}
