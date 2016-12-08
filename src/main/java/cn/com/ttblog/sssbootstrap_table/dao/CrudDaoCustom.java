package cn.com.ttblog.sssbootstrap_table.dao;

public interface CrudDaoCustom<T,ID>{
    /**
     * 获取所有记录数
     * @return long类型数量
     */
    long getTotal();
}
