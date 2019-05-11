package cn.itcast.core.controller;

import cn.itcast.core.service.SpecificationService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.SpecificationVo;

import java.util.List;
import java.util.Map;

/**
 * @author niyueyeee
 * @create 2019-04-26 21:17
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Reference
    private SpecificationService specificationService;

    //规格管理之列表查询 有条件 有分页
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody cn.itcast.core.pojo.specification.Specification specification) {
        return specificationService.search(page, rows, specification);
    }

    //添加规格
    @RequestMapping("/add")
    public Result add(@RequestBody SpecificationVo Vo) {
        try {
            specificationService.add(Vo);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    //修改规格
    @RequestMapping("/update")
    public Result update(@RequestBody SpecificationVo Vo) {
        try {
            specificationService.update(Vo);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    //删除规格
    @RequestMapping("/delete")
    public Result delete(long[] ids) {
        try {
            specificationService.delete(ids);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    //查询一个规格
    @RequestMapping("/findOne")
    public SpecificationVo findOne(Long id) {
        return specificationService.findOne(id);
    }

    //查询所有规格 返回值为List<Map>
    @RequestMapping("selectOptionList")
    public List<Map> selectOptionList() {
        return specificationService.selectOptionList();
    }
}
