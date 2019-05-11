package cn.itcast.core.controller;

import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author niyueyeee
 * @create 2019-04-29 20:09
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    //查询
    @RequestMapping("/findByParentId")
    public List<cn.itcast.core.pojo.item.ItemCat> findByParentId(Long parentId) {
        return itemCatService.findByParentId(parentId);
    }

    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody cn.itcast.core.pojo.item.ItemCat itemCat) {
        try {
            itemCatService.add(itemCat);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    //回显
    @RequestMapping("/findOne")
    public cn.itcast.core.pojo.item.ItemCat findOne(Long id) {
        return itemCatService.findOne(id);
    }

    //修改
    @RequestMapping("/update")
    public Result update(@RequestBody cn.itcast.core.pojo.item.ItemCat itemCat) {
        try {
            itemCatService.update(itemCat);
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
            itemCatService.delete(ids);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    //查询所有
    @RequestMapping("/findAll")
    public List<cn.itcast.core.pojo.item.ItemCat> findAll() {
        return itemCatService.findAll();
    }
}
