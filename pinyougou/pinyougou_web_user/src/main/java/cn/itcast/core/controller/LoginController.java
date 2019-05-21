package cn.itcast.core.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author NiYueYeee
 * @create 2019-05-20 18:38
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/name")
    public Map<String,String> name(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        HashMap<String, String> map = new HashMap<>();
        map.put("loginName",name);
        return map;
    }
}
