package com.wp.service.impl;

import com.wp.domain.User;
import com.wp.dao.UserDao;
import com.wp.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 翁鹏
 * @since 2023-02-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

    @Autowired
    UserDao userDao;
    @Override
    public User queryUserByName(String username) {
        return userDao.queryUserByName(username);
    }


    @Override
    public String registerService(User user) {
        return null;
    }
}
