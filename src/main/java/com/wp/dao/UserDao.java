package com.wp.dao;

import com.wp.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 翁鹏
 * @since 2023-02-11
 */
@Repository
@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("select * from tbl_user where username = #{username}")
    public User queryUserByName(String username);

}
