package com.wp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wp.domain.Music;
import com.wp.dao.MusicDao;
import com.wp.service.IMusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 翁鹏
 * @since 2023-02-12
 */
@Service
public class MusicServiceImpl extends ServiceImpl<MusicDao, Music> implements IMusicService {

    @Autowired
    MusicDao musicDao;

    @Override
    public void addMusicHistory(Object data) {

        // Object转换成JSONObject
        JSONObject jsonData=(JSONObject)JSONObject.toJSON(data);

        System.out.println("jsonData"+jsonData);
        System.out.println("======");
        JSONArray list=(JSONArray)jsonData.get("list");
        //遍历，存入数据库
        for (int i=0;i<list.size();i++){
            JSONObject jo=list.getJSONObject(i);
            Integer rid=jo.getInteger("rid");
            String album=jo.getString("album");
            String artist=jo.getString("artist");
            String name=jo.getString("name");
            String duration=jo.getString("duration");

            //防止重复
            Integer count=musicDao.selectCount(new QueryWrapper<Music>().eq("rid",rid)
                    .eq("album",album)
                    .eq("artist",artist)
                    .eq("name",name)
                    .eq("duration",duration));

            //无重复数据
            if (count<=0){

                Music music=new Music();
                music.setRid(rid);
                music.setAlbum(album);
                music.setArtist(artist);
                music.setName(name);
                music.setDuration(duration);

                musicDao.insert(music);
            }
        }

    }

    @Override
    public Music getMusicByRid(Integer rid) {
        Music m= musicDao.selectOne(new QueryWrapper<Music>().eq("rid",rid));

        return m;
    }




}
