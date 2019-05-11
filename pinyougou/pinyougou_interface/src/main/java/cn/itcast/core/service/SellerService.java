package cn.itcast.core.service;

import entity.PageResult;

/**
 * @author niyueyeee
 * @create 2019-04-28 21:43
 */
public interface SellerService {
    void add(cn.itcast.core.pojo.seller.Seller seller);

    cn.itcast.core.pojo.seller.Seller findOne(String username);

    PageResult search(Integer page, Integer rows, cn.itcast.core.pojo.seller.Seller seller);

    void updateStatus(String sellerId, String status);
}
