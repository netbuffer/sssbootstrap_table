package cn.com.ttblog.sssbootstrap_table.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *  jpa注解标记在get方法上,get方法上没有加注解标识默认为@Basic注解映射
 *  @Transient注解不需要映射的方法
 * 	@Temporal(TemporalType.DATE/TIME/TIMESTAMP)注解时间类型对应的三种类型精度
 */
//在dao接口中定义findByN方法后，spring data jpa会自动解析来匹配这个调用的
@NamedQuery(name = "User.findByN", query = "select u from User u where u.name = ?1")
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
	//引用ValidationMessages资源文件中key为username.valid的信息
	@Size(min = 1, max = 20,message = "{username.valid}")
	private String name;
	private String sex;
	@NotNull(message = "年龄不为空")
	//引用国际化资源文件key为Range.user.age的信息,message = "{Range.user.age}"
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
	//jpa框架会维护version版本号字段 update ... where version=.. 当version字段被变更和之前读到的记录不符合，执行update就会出错
	private Integer version;

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
	@OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
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
	@OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
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
				String comments,int version) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.phone = phone;
		this.deliveryaddress = deliveryaddress;
		this.adddate = adddate;
		this.comments = comments;
		this.version=version;
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

	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version=version;
	}

	public String toString() {
		//允许打印transient成员
		return ToStringBuilder.reflectionToString(this,null,true);
	}

}