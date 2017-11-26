package cn.com.ttblog.sssbootstrap_table.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * MySQL error 150：http://www.examw.com/biancheng/mysql/165562/
 * InnoDB引擎下teamCode为非主键列需要做唯一索引，才能在leader表中创建外键引用
   ALTER TABLE `t_team` ADD UNIQUE INDEX `team_code_unique_index` (`teamCode`) ;
 */
@Entity
@Table(name = "t_team")
public class Team {

    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String name;
    @Column(unique = true)
    private String teamCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
