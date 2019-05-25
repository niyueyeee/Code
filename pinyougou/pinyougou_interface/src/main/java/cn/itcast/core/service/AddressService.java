package cn.itcast.core.service;

import java.util.List;

/**
 * @author NiYueYeee
 * @create 2019-05-23 16:16
 */
public interface AddressService {
    List<cn.itcast.core.pojo.address.Address> findListByLoginUser(String name);
}
