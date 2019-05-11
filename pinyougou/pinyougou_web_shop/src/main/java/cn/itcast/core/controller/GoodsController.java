package cn.itcast.core.controller;

import cn.itcast.core.service.GoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.GoodsVo;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {


    @Reference
    private GoodsService goodsService;

    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody GoodsVo goodsVo) {

        try {
            //获取商品的id
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            goodsVo.getGoods().setSellerId(name);
            goodsService.add(goodsVo);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    //修改
    @RequestMapping("/update")
    public Result update(@RequestBody GoodsVo goodsVo) {

        try {
            goodsService.update(goodsVo);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    //分页
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody cn.itcast.core.pojo.good.Goods goods) {
        //当前登陆人
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.setSellerId(name);
        return goodsService.search(page, rows, goods);
    }

    //查询包装类
    @RequestMapping("/findOne")
    public GoodsVo findOne(Long id) {
        return goodsService.findOne(id);
    }
}



