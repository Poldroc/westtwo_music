package com.wp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 翁鹏
 * @since 2023-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tbl_music")
public class Music implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer rid;

    private String name;

    private String artist;

    private String album;

    private String duration;


}
