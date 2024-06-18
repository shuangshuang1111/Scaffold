package com.integration.scaffold.relationaldataaccess.mysql.service;

import com.integration.scaffold.relationaldataaccess.mysql.dto.UserAddressBookDto;
import com.integration.scaffold.relationaldataaccess.mysql.entity.AddressBook;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.concurrent.Future;

public interface AddressBookService {
    AddressBook save(AddressBook addressBook);

    AddressBook getById(Long id);

    List<AddressBook> listByUserId(Long userId);

    void deleteById(Long id);


    UserAddressBookDto getAllUserInfo(Long id);


    Page<AddressBook> getUserInfoByPage(Long userId, int pageNum, int pageSize);


    List<String> getAllConsignee();

    void testSyncInsertDatas();

    Integer testAsyncInsertDatas();
}
