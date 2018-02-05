package cn.com.ttblog.sssbootstrap_table.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 联合主键测试
 */
@IdClass(UserFavoritesPK.class)
@Entity
@Table(name = "user_favorites")
public class UserFavorites implements Serializable{

    private Long userId;
	private Long goodsId;
    private String remark;

    @Id
    @Column(name = "user_id")
	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "goods_id")
    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
