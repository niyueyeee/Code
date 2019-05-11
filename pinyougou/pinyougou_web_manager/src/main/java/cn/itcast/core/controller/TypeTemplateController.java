package cn.itcast.core.controller;

import cn.itcast.core.service.TypeTemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niyueyeee
 * @create 2019-04-27 15:30
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
    @Reference
    private TypeTemplateService typeTemplateService;

    //显示所有 加分页数据
    @RequestMapping("search")
    public PageResult search(Integer page, Integer rows, @RequestBody cn.itcast.core.pojo.template.TypeTemplate tt) {
        return typeTemplateService.search(page, rows, tt);
    }

    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody cn.itcast.core.pojo.template.TypeTemplate tt) {
        try {
            typeTemplateService.add(tt);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    //查询一个数据
    @RequestMapping("/findOne")
    public cn.itcast.core.pojo.template.TypeTemplate findOne(Long id) {
        return typeTemplateService.findOne(id);

    }

    //修改数据
    @RequestMapping("/update")
    public Result update(@RequestBody cn.itcast.core.pojo.template.TypeTemplate tt) {
        try {
            typeTemplateService.update(tt);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    //删除数据
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            typeTemplateService.delete(ids);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }
}
