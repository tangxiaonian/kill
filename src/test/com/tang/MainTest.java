package com.tang;

import com.tang.kill.SpringKillApplication;
import com.tang.kill.controller.TbKillShopController;
import com.tang.kill.dto.KillDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Classname MainTest
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/3/22 7:54
 * @Created by ASUS
 */
@SpringBootTest(classes = SpringKillApplication.class)
@RunWith(SpringRunner.class)
public class MainTest {

    @Resource
    private TbKillShopController tbKillShopController;

    @Test
    public void test01(){

        CountDownLatch countDownLatch = new CountDownLatch(500);

        Random random = new Random();

        ExecutorService executorService = Executors.newFixedThreadPool(500);

        executorService.execute(new TestRunner(countDownLatch,tbKillShopController,
                new KillDto(random.nextInt(3) + 1,3)));

        executorService.shutdown();

    }

    @Test
    public void test02(){

        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int n = random.nextInt(3);

            System.out.println( n + 1);
        }


    }

}

class TestRunner implements Runnable{

    private CountDownLatch countDownLatch;
    private TbKillShopController tbKillShopController;
    private KillDto killDto;

    public TestRunner(CountDownLatch countDownLatch,
                      TbKillShopController tbKillShopController, KillDto killDto) {
        this.countDownLatch = countDownLatch;
        this.tbKillShopController = tbKillShopController;
        this.killDto = killDto;
    }

    @Override
    public void run() {

        System.out.println( Thread.currentThread().getName() + "线程进来，开始秒杀....." );

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tbKillShopController.killShop(killDto);

        System.out.println( Thread.currentThread().getName() + "线程出去，秒杀结束....." );

    }
}