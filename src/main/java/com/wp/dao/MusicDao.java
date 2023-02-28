package com.wp.dao;

import com.wp.domain.Music;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 翁鹏
 * @since 2023-02-12
 */

@Repository
@Mapper
public interface MusicDao extends BaseMapper<Music> {

}
