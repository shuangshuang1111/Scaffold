package com.integration.scaffold.relationaldataaccess.mysql;

import com.integration.scaffold.relationaldataaccess.mysql.service.impl.AddressBookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class ConverTest {

    // 同步方法
    @Test
    void whenUsingNonAsync_thenMainThreadIsUsed() throws Exception {
        CompletableFuture<String> name = CompletableFuture.supplyAsync(() -> "Baeldung");
        printCurrentThread();
        CompletableFuture<Integer> nameLength = name.thenApply(value -> {
            printCurrentThread(); // will print "main"
            return value.length();
        });

        assertThat(nameLength.get()).isEqualTo(8);
    }

    private static void printCurrentThread() {
        System.out.println(Thread.currentThread().getName());
    }
    // 异步方法

    @Test
    void whenUsingAsync_thenUsesCommonPool() throws Exception {
        CompletableFuture<String> name = CompletableFuture.supplyAsync(() -> "Baeldung");
        printCurrentThread();
        CompletableFuture<Integer> nameLength = name.thenApplyAsync(value -> {
            printCurrentThread(); // will print "ForkJoinPool.commonPool-worker-1"
            return value.length();
        });

        assertThat(nameLength.get()).isEqualTo(8);
    }

    @Test
    void test() throws Exception {
        Integer total = AddressBookServiceImpl.TOTAL_INSERT_DATA;
        int expectedlength = total * 2 + 2;
        System.out.println(expectedlength);
    }

}
