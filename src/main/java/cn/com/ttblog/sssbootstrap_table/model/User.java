package cn.com.ttblog.sssbootstrap_table.model;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;

/**
 *  jpa注解标记在get方法上,get方法上没有加注解标识默认为@Basic注解映射
 *  @Transient注解不需要映射的方法
 * 	@Temporal(TemporalType.DATE/TIME/TIMESTAMP)注解时间类型对应的三种类型精度
 */
@Entity
@Table(name = "user")
@XmlRootElement
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4193866401992779318L;
	/**
	 * 用户id
	 */
	private Long id;
	private String name;
	private String sex;
	@Size(min = 1, max = 20, message = "{用户名长度必须在1到20个字符之间}")
	@NotNull(message = "年龄不能为空并且在1-150之间")
	@Range(min = 1, max = 150)
//	@NumberFormat() 可以设置时间日期的格式化
	private Integer age;
	private String phone;
	private String deliveryaddress;
	private Integer adddate;
	private Card card;
	private transient String comments;
	// 用户使用的地址
	private List<Address> addresses;
//	private String[] img;
//
//	public String[] getImg() {
//		return img;
//	}
//
//	public void setImg(String[] img) {
//		this.img = img;
//	}

	@Transient
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * address表的user_id字段
	 * @return
     */
	@OneToMany
	@JoinColumn(name = "user_id")
	@Access(AccessType.PROPERTY)//不添加此注解会抛错误:Could not determine type for: java.util.List, at table: user, for columns: [org.hibernate.mapping.Column(addresses)]
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getDeliveryaddress() {
		return deliveryaddress;
	}

	public void setDeliveryaddress(String deliveryaddress) {
		this.deliveryaddress = deliveryaddress == null ? null : deliveryaddress.trim();
	}

	public Integer getAdddate() {
		return adddate;
	}

	public void setAdddate(Integer adddate) {
		this.adddate = adddate;
	}

	/**
	 * 使用user表的id字段
	 * @return
     */
	@OneToOne
	@JoinColumn(name = "id")
	@Access(AccessType.PROPERTY)
	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public User(String name, String sex, Integer age, String phone, String deliveryaddress, Integer adddate,
				String comments) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.phone = phone;
		this.deliveryaddress = deliveryaddress;
		this.adddate = adddate;
		this.comments = comments;
	}

	public User(String name, String sex, Integer age, String phone, String deliveryaddress, Integer adddate,
			String comments, List<Address> addresses, Card card) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.phone = phone;
		this.deliveryaddress = deliveryaddress;
		this.adddate = adddate;
		this.comments = comments;
		this.addresses = addresses;
		this.card = card;
	}

	public User() {

	}
	
	public void init(){
		System.out.println("user init!");
	}
	
//	public synchronized void syn(){
//		System.out.println("syn!");
//		try {
//			TimeUnit.SECONDS.sleep(20);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void syn(){
		synchronized(this){
			System.out.println("syn!");
			try {
				TimeUnit.SECONDS.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void syn2() {
		System.out.println("syn2!");
		try {
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Transient
	public String getUserInfo(){
		return "用户名:"+this.name;
	}

	public String toString() {
		//允许打印transient成员
		return ToStringBuilder.reflectionToString(this,null,true);
	}

}