package cn.itcast.core.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author niyueyeee
 * @create 2019-04-28 21:23
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/showName")
    public Map<String, Object> showName() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> map = new HashMap<>();
        map.put("username", name);
        map.put("cur_time", new Date());
        return map;
    }
}
