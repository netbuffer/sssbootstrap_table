package cn.com.ttblog.sssbootstrap_table.model;

import java.io.Serializable;

public class UserFavoritesPK implements Serializable {

    private Long userId;
    private Long goodsId;

    public UserFavoritesPK(){

    }

    public UserFavoritesPK(Long userId,Long goodsId){
        this.userId=userId;
        this.goodsId=goodsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}