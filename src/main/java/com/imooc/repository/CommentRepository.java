package com.imooc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.dataobject.Comment;

/**
 * Created by qcl on 2019-03-31
 * 微信：2501902696
 * desc:
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByOpenid(String openid);
    List<Comment> findAllByOpenidAndOrderId(String openid,String orderId);
}
