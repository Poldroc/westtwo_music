package com.wp.service;

import com.wp.domain.History;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wp.domain.Music;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 翁鹏
 * @since 2023-02-13
 */
@Transactional
public interface IHistoryService extends IService<History> {

    public Boolean delete(Integer id);


    public Boolean deleteByList(List list);


    public Boolean updataFav(History h);

    public void  addDownloadHistory(Music m,History h);




}
