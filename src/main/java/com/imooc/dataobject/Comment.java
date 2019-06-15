package com.imooc.dataobject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * Created by qcl on 2019-03-31
 * 微信：2501902696
 * desc:
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Comment {

    @Id
    @GeneratedValue
    private int commentId;
    private String orderId;
    private String openid;
    private String name;
    //头像地址
    private String avatarUrl;
    private String content;
    private Date createTime;



}
