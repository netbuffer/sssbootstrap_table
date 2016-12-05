package cn.com.ttblog.sssbootstrap_table.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "t_crud_demo")
public class CrudModel {
    private Long id;
    private String name;
    private Date createtime;
    private CrudSubModel crudSubModel;


    public CrudModel() {

    }

    /**
     * 使用t_crud_demo.sub_id来join t_crud_demosub.id字段
     * @return
     */
    @OneToOne
    @JoinColumn(name = "sub_id")
    public CrudSubModel getCrudSubModel() {
        return crudSubModel;
    }

    public void setCrudSubModel(CrudSubModel crudSubModel) {
        this.crudSubModel = crudSubModel;
    }

    public CrudModel(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
