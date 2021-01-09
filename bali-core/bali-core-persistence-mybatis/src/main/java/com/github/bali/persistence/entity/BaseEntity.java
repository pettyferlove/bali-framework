package com.github.bali.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author Petty
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseEntity<T> extends Model<BaseEntity<T>> {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableLogic
    private Integer delFlag;

    private String creator;

    private LocalDateTime createTime;

    private String modifier;

    private LocalDateTime modifyTime;

}
