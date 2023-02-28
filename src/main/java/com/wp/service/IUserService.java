package com.wp.service;

import com.wp.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 翁鹏
 * @since 2023-02-11
 */
public interface IUserService extends IService<User> {


    public User queryUserByName(String username);

    public String registerService(User user);
}
