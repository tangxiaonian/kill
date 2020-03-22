package com.tang.kill.controller;

import com.tang.kill.dto.KillDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ASUS
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @Resource
    private TbKillShopController tbKillShopController;

    /**
     * 并发的线程数
     */
    private static final Integer ThreadCount =5000;


    @GetMapping("/start")
    public String test() {

        CountDownLatch countDownLatch = new CountDownLatch(ThreadCount);

        Random random = new Random();

        ExecutorService executorService = Executors.newFixedThreadPool(ThreadCount);

        for (int i = 0; i < ThreadCount; i++) {
            executorService.execute(new TestRunner(countDownLatch, tbKillShopController,
                    new KillDto(random.nextInt(5) + 1, 3)));
        }

        executorService.shutdown();

        return "index";
    }


}

class TestRunner implements Runnable{

    private CountDownLatch countDownLatch;
    private TbKillShopController tbKillShopController;
    private KillDto killDto;

    TestRunner(CountDownLatch countDownLatch,
               TbKillShopController tbKillShopController, KillDto killDto) {
        this.countDownLatch = countDownLatch;
        this.tbKillShopController = tbKillShopController;
        this.killDto = killDto;
    }

    @Override
    public void run() {

        System.out.println( Thread.currentThread().getName() + "线程进来，开始秒杀....." );

        countDownLatch.countDown();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tbKillShopController.killShop(killDto);

        System.out.println( Thread.currentThread().getName() + "线程出去，秒杀结束....." );

    }
}