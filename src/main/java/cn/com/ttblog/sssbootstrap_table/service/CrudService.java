package cn.com.ttblog.sssbootstrap_table.service;

import cn.com.ttblog.sssbootstrap_table.dao.CrudDao;
import cn.com.ttblog.sssbootstrap_table.model.CrudModel;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class CrudService {
    @Resource
    private CrudDao crudDao;

    public CrudModel save(CrudModel crudModel){
        return crudDao.save(crudModel);
    }

    public Iterable<CrudModel> findAll(){
        return crudDao.findAll();
    }

    public CrudModel findOne(Long id){
        return crudDao.findOne(id);
    }

    public int deleteById(Long id){
        return crudDao.deleteById(id);
    }
}
