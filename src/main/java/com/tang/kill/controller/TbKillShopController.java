package com.tang.kill.controller;


import com.tang.kill.domain.ResponseResult;
import com.tang.kill.dto.TbKillShop;
import com.tang.kill.dto.KillDto;
import com.tang.kill.service.TbKillShopServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author ASUS
 */
@RequestMapping("/kill")
@RestController
public class TbKillShopController {

    @Resource
    private TbKillShopServiceImpl tbKillShopServiceImpl;

    @GetMapping("/getKillShop")
    public List<TbKillShop> getKillShop() {
        return tbKillShopServiceImpl.getKillShop();
    }

    @PostMapping("/shop")
    public ResponseResult<String> killShop(@RequestBody KillDto killDto) {

        System.out.println( Thread.currentThread().getName() + "请求进来，准备抢购......." );

//        Boolean result = tbKillShopServiceImpl.killShop(killDto);

        Boolean result = tbKillShopServiceImpl.killShopV1(killDto);

        if (result) {
            return ResponseResult.Ok(null);
        }

        return ResponseResult.Fail(null);
    }

    @PostMapping("/tbshop")
    public ResponseResult<String> shop(@RequestBody com.tang.kill.domain.TbShop tbShop) {
        System.out.println( tbShop );
        return null;
    }

}