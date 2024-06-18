package com.integration.scaffold.relationaldataaccess.mysql.mapper;


import com.integration.scaffold.relationaldataaccess.mysql.entity.UserBasic;
import org.springframework.data.repository.CrudRepository;

public interface UserBasicRepository extends CrudRepository<UserBasic, Long> {

    long countByName(String name);

    UserBasic queryById(Long id);

}
