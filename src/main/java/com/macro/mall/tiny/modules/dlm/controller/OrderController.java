package com.macro.mall.tiny.modules.dlm.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.dlm.model.Order;
import com.macro.mall.tiny.modules.dlm.service.OrderService;
import com.macro.mall.tiny.modules.dlm.vo.OrderVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    //后台列表查询
    @RequestMapping("/getOrderList")
    public CommonResult getOrderList(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                     @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                     String phone,
                                     String startTime,
                                     String endTime,
                                     String openId,
                                     String customerName){
        if(startTime != null && !"".equals(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(endTime != null && !"".equals(endTime)){
            endTime = endTime + " 23:59:59";
        }
        Page<OrderVO> pageResult = new Page(page,pageSize);
        return CommonResult.success(orderService.getOrderList(pageResult,phone,startTime,endTime,openId,customerName));
    }

    @RequestMapping("/getUserOrderPages")
    public CommonResult getUserOrderPages(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                    @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                    String phone,
                                    String startTime,
                                    String endTime,
                                    String customerName,
                                    String goodsName){
        if(startTime != null && !"".equals(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(endTime != null && !"".equals(endTime)){
            endTime = endTime + " 23:59:59";
        }
        Page<OrderVO> pageResult = new Page(page,pageSize);
        return CommonResult.success(orderService.getUserOrderPages(pageResult,phone,startTime,endTime,customerName,goodsName));
    }


    //加入购物车(每个商品次加入购物车都需要访问的接口)
//    @RequestMapping("/insertUserOrder")
//    public UserOrder insertUserOrder(UserOrder userOrder){
//        return orderService.insertUserOrder(userOrder);
//    }

    //手机端查询个人订单
    @RequestMapping("/getOrderListByOpenId")
    public CommonResult getOrderListByOpenId(@RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                               String openId){
        Page<OrderVO> pageResult = new Page(page,pageSize);
        return CommonResult.success(orderService.getOrderListByOpenId(pageResult,openId));
    }

    //提交订单
    @RequestMapping("/submitOrder")
    public CommonResult<Order> submitOrder(Order order,String userOrderJson,Integer shopStatus){
        return CommonResult.success(orderService.submitOrder(order,userOrderJson,shopStatus));
    }

    //提交订单
    @RequestMapping("/cancelOrder")
    public CommonResult<Order> cancelOrder(Order order){
        return CommonResult.success(orderService.cancelOrder(order));
    }
}
