package com.wp.service;

import com.wp.domain.Music;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 翁鹏
 * @since 2023-02-12
 */
@Transactional
public interface IMusicService extends IService<Music> {
    public void addMusicHistory(Object data);

    public Music getMusicByRid(Integer rid);

}
