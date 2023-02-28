package com.wp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wp.domain.History;
import com.wp.dao.HistoryDao;
import com.wp.domain.Music;
import com.wp.service.IHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 翁鹏
 * @since 2023-02-13
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryDao, History> implements IHistoryService {

    @Autowired
    HistoryDao historyDao;

    @Override
    public Boolean delete(Integer id) {
        return historyDao.deleteById(id)>0;
    }

    @Override
    public Boolean deleteByList(List list) {
        return historyDao.deleteBatchIds(list)>0;
    }

    @Override
    public Boolean updataFav(History h) {
        return historyDao.updateById(h)>0;
    }

    @Override
    public void addDownloadHistory(Music m, History h) {
        Integer count=historyDao.selectCount(new QueryWrapper<History>().eq("rid",m.getRid())
                .eq("album",m.getAlbum())
                .eq("artist",m.getArtist())
                .eq("name",m.getName())
                .eq("duration",m.getDuration()));
        if (count<=0){
            h.setAlbum(m.getAlbum());
            h.setName(m.getName());
            h.setRid(m.getRid());
            h.setDuration(m.getDuration());
            h.setArtist(m.getArtist());

            System.out.println("=====++++"+h);
            historyDao.insert(h);
        }
    }


}
