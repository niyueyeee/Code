package cn.itcast.core.service;

import cn.itcast.core.pojo.user.User;

/**
 * @author NiYueYeee
 * @create 2019-05-16 14:25
 */
public interface UserService {
    void sendCode(String phone);

    void add(String smscode, User user);
}
