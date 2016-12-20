package cn.com.ttblog.sssbootstrap_table.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 使用lombok简化代码 https://projectlombok.org/features/index.html
 * @package cn.com.ttblog.sssbootstrap_table.model
 * @author netbuffer
 */
@Entity
@Table(name = "card")
public class Card implements Serializable{
	private Long userId;
	private String cardNo;
    public Card(){

    }
    public Card(Long userId, String cardNo) {
        this.userId = userId;
        this.cardNo = cardNo;
    }

	@Id
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

	@Column(name = "card_no")
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}