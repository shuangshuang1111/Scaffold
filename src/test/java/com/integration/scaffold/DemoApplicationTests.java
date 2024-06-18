package com.integration.scaffold;

import com.integration.scaffold.relationaldataaccess.mysql.entity.UserBasic;
import com.integration.scaffold.relationaldataaccess.mysql.mapper.AddressBookRepository;
import com.integration.scaffold.relationaldataaccess.mysql.mapper.UserBasicRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserBasicRepository userBasicRepository;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    void contextLoads() {
        long count = userBasicRepository.countByName("xiaohong");
        System.out.println(count);
    }

    @Test
    void testSave() {
        boolean actualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        System.out.println(actualTransactionActive);
        UserBasic userBasic = new UserBasic();
        userBasic.setName("xiaohong");
        userBasicRepository.save(userBasic);
        System.out.println(userBasic);
        long count = userBasicRepository.countByName("xiaohong");
        UserBasic userBasic1 = userBasicRepository.queryById(1L);
        System.out.println(count);
        System.out.println(userBasic1);
    }

    @Test
    @Transactional
    void testSaveTran() {
        assertTrue(TransactionSynchronizationManager.isActualTransactionActive());
        boolean actualTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        System.out.println(actualTransactionActive);
        UserBasic userBasic = new UserBasic();
        userBasic.setName("xiaohong");
        userBasicRepository.save(userBasic);
        System.out.println(userBasic);
        long count = userBasicRepository.countByName("xiaohong");
        UserBasic userBasic1 = userBasicRepository.queryById(1L);
        System.out.println(count);
        System.out.println(userBasic1);
    }

//	@Test
//	void testBooks(){
////		List<AddressBook> strs = addressBookRepository.findDistinctByConsignee();
//		List<AddressBook> strs = addressBookRepository.findByConsignee();
//		System.out.println(strs);
//	}

}
