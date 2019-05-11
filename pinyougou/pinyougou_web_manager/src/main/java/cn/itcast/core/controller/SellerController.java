package cn.itcast.core.controller;

import cn.itcast.core.service.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niyueyeee
 * @create 2019-04-29 9:47
 */
@RestController
@RequestMapping("/seller")
public class SellerController {
    @Reference
    private SellerService sellerService;

    //查询未审核
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody cn.itcast.core.pojo.seller.Seller seller) {
        return sellerService.search(page, rows, seller);
    }

    //回显
    @RequestMapping("/findOne")
    public cn.itcast.core.pojo.seller.Seller findOne(String id) {
        return sellerService.findOne(id);
    }

    //修改
    @RequestMapping("/updateStatus")
    public Result updateStatus(String sellerId, String status) {
        try {
            sellerService.updateStatus(sellerId, status);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }
}
