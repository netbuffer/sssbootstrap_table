package cn.netbuffer.sssbootstrap_table.dao;

import cn.netbuffer.sssbootstrap_table.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ICardDao extends JpaRepository<Card,Long>,JpaSpecificationExecutor<Card> {
}