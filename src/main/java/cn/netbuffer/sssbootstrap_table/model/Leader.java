package cn.netbuffer.sssbootstrap_table.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import javax.persistence.*;

@Entity
@Table(name = "t_leader")
public class Leader {

    private Long id;
    private String name;

    private String teamCode;
    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    private Team team;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //非主键列关联
    @OneToOne
    @JoinColumn(name = "teamCode",referencedColumnName = "teamCode",insertable = false,updatable = false)
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
