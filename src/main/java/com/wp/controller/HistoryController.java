package com.wp.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wp.dao.HistoryDao;
import com.wp.domain.History;
import com.wp.service.impl.HistoryServiceImpl;
import com.wp.util.Code;
import com.wp.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 翁鹏
 * @since 2023-02-13
 */
@RestController

public class HistoryController {
    @Autowired
    HistoryDao historyDao;

    @Autowired
    HistoryServiceImpl historyService;


    /**
     * 分页查询下载记录
     * @param page
     * @return
     */
    @GetMapping("user/history")
    public Result getDLHistory(@RequestParam(defaultValue = "1") String page){
        //每页10条
        IPage<History> pageInfo=new Page<>(Long.parseLong(page),10);
        //执行分页查询
        Page<History> historyPage= historyDao.selectPage(new Page<>(Long.parseLong(page),10),null);

        Map<String,Object> r=new HashMap<>();
        r.put("list", historyPage.getRecords());
        r.put("count",historyPage.getPages());

        return new Result(Code.OK,r,"success");

    }

    /**
     * 删除下载记录
     * @param params
     * @return
     */


    @DeleteMapping("user/history")
    public Result deleteHistory(@RequestBody Map<String,Object> params){

        System.out.println("===params==="+params);


        Integer type=(Integer)params.get("type");
        Integer id=(Integer)params.get("id");
        List<Integer> list = JSONObject.parseObject(JSONObject.toJSONString(params.get("list")),ArrayList.class);

        System.out.println("===list==="+list);


        //根据列表id删除
        if (type==1){
            boolean flag = historyService.deleteByList(list);
            return new  Result(flag?Code.OK:Code.ERR,null,flag?"success":"error");
        } else if (type==0) { //根据id删除
            boolean flag = historyService.delete(id);
            return new  Result(flag?Code.OK:Code.ERR,null,flag?"success":"error");
        }else {
            return new Result(Code.ERR,null,"error");
        }
    }

    /**
     * 修改收藏
     * @param parmas
     * @return
     */

    @PutMapping("user/history/lc")
    public Result updataFav(@RequestBody Map<String,Object> parmas){
        Integer id=(Integer)parmas.get("id");
        Integer fav=(Integer)parmas.get("fav");

        Map<String,Object> data=new HashMap<>();
        History selectHtr=historyDao.selectById(id);
        data.put("name",selectHtr.getName());
        data.put("rid",selectHtr.getRid());
        data.put("artist",selectHtr.getArtist());
        data.put("album",selectHtr.getAlbum());
        data.put("duration",selectHtr.getDuration());

        History updataHtr=new History();
        updataHtr.setId(id);
        updataHtr.setFav(fav);
        Boolean flag = historyService.updataFav(updataHtr);
        if (flag){
            return new Result(Code.OK,data,"success");
        }else {
            return new Result(Code.ERR,null,"error");
        }






    }




}

