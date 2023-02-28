package com.wp.controller;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wp.dao.HistoryDao;
import com.wp.dao.MusicDao;
import com.wp.domain.History;
import com.wp.domain.Music;
import com.wp.realm.UserRealm;
import com.wp.service.impl.HistoryServiceImpl;
import com.wp.service.impl.MusicServiceImpl;
import com.wp.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 翁鹏
 * @since 2023-02-12
 */
@RestController

public class MusicController {

    public static Integer tempRid=null;

    @Autowired
    MusicDao musicDao;


    @Autowired
    HistoryDao historyDao;

    @Autowired
    MusicServiceImpl musicService;

    @Autowired
    HistoryServiceImpl historyService;

    /**
     * 搜索音乐并添加历史记录
     * @param pn
     * @param rn
     * @param text
     * @return
     */

    @GetMapping("/search")
    public Result KWSearch(@RequestParam(defaultValue = "1") Integer pn,
                           @RequestParam(defaultValue = "10") Integer rn,
                           @RequestParam(defaultValue = "") String text){

        //调用酷我音乐api得到歌曲数据
        Object data= GetKWMusic.getKWByKey(text, pn, rn);

        //添加搜索歌曲的历史记录
        musicService.addMusicHistory(data);

        return new Result(Code.OK,data,"success");
    }


    /**
     * 下载音乐
     * @param rid
     * @return
     */

    @GetMapping("/search/download/{rid}")
    public void download(@PathVariable("rid") Integer rid, HttpServletResponse response,HttpServletRequest request)  {

        //添加下载记录
        //1.通过rid在搜索记录中查找music
        Music m=musicService.getMusicByRid(rid);
        History h=new History();

        //2.添加下载记录，不重复
        historyService.addDownloadHistory(m,h);


        //下载文件

        String fileName=rid+".mp3";
        String url="https://link.hhtjim.com/kw/"+rid+".mp3";

        FileUtil.downloadHttpFile(url,request,response,fileName);
        //return new ModelAndView("redirect:https://link.hhtjim.com/kw/"+rid+".mp3") ;

    }
}

