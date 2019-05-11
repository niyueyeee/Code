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
        //System.out.println(goods.getGoodsName());
        return goodsService.search(page, rows, goods);
    }

    //查询包装类
    @RequestMapping("/findOne")
    public GoodsVo findOne(Long id) {
        return goodsService.findOne(id);
    }

    //更新状态
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            goodsService.updateStatus(ids, status);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }
}



