package cn.itcast.core.controller;

import entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NiYueYeee
 * @create 2019-05-15 21:28
 */
@RestController
@RequestMapping("/user")
public class UserController {
    //发短信验证码
    @RequestMapping("/sendCode")
    public Result sendCode(String phone) {
        try {

            //1:判断手机号的合法性
            //2:调用用户接口或实现类 入参手机号


            return new Result(true, "发短信成功");
        } catch (Exception e) {
            return new Result(false, "发短信失败");

        }

    }
}
