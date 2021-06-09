package com.macro.mall.tiny.modules.dlm.service;

import com.macro.mall.tiny.modules.dlm.mapper.GoodsMapper;
import com.macro.mall.tiny.modules.dlm.mapper.GoodsOptionMapper;
import com.macro.mall.tiny.modules.dlm.mapper.OrderMapper;
import com.macro.mall.tiny.modules.dlm.mapper.UserOrderMapper;
import com.macro.mall.tiny.modules.dlm.exception.MyEumException;
import com.macro.mall.tiny.modules.dlm.exception.MyException;
import com.macro.mall.tiny.modules.dlm.model.*;
import com.macro.mall.tiny.modules.dlm.util.CommonUtil;
import com.macro.mall.tiny.modules.dlm.vo.OrderVO;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("orderService")
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserOrderMapper userOrderMapper;

    @Resource
    private GoodsMapper goodsMapper;


    @Resource
    private GoodsOptionMapper goodsOptionMapper;

    public IPage<OrderVO> getOrderList(Page<OrderVO> page,String phone,String startTime,String endTime,String openId,String customerName){
        //分页获取订单列表
        IPage<OrderVO> orderVOPage = orderMapper.getOrderList(page,phone,startTime,endTime,openId,customerName);
        List<OrderVO> orderVOList = orderVOPage.getRecords();
        if(!CommonUtil.AssertEmpty(orderVOList)){
            for(OrderVO orderVO : orderVOList){
                List<UserOrderVO> userOrderVOList = userOrderMapper.getUserOrderByOrderId(orderVO.getId());
                orderVO.setOrderDetailList(userOrderVOList);
            }
        }

        return orderVOPage;
    }

    public List<OrderVO> getAllOrderList(String phone,String startTime,String endTime,String openId,String customerName){
        //分页获取订单列表
        List<OrderVO> orderVOList = orderMapper.getAllOrderList(phone,startTime,endTime,openId,customerName);
        if(!CommonUtil.AssertEmpty(orderVOList)){
            for(OrderVO orderVO : orderVOList){
                List<UserOrderVO> userOrderVOList = userOrderMapper.getUserOrderByOrderId(orderVO.getId());
                orderVO.setOrderDetailList(userOrderVOList);
            }
        }

        return orderVOList;
    }

    //分页获取具体下单货物列表
    public IPage<UserOrderVO> getUserOrderPages(Page<OrderVO> page,String phone,String startTime,String endTime,String customerName,String goodsName){
        IPage<UserOrderVO> userOrderVOIPage = userOrderMapper.getUserOrderPages(page, phone, startTime, endTime,customerName,goodsName);

        return userOrderVOIPage;
    }

    /***
     * 手机端查询个人订单
     * @param page
     * @param openId
     * @return
     */
    public IPage<OrderVO> getOrderListByOpenId(Page<OrderVO> page,String openId){
        IPage<OrderVO> orderVOIPage = orderMapper.getOrderListByOpenId(page, openId);
        List<OrderVO> orderVOList = orderVOIPage.getRecords();
        if(!CommonUtil.AssertEmpty(orderVOList)){
            for(OrderVO orderVO : orderVOList){
                orderVO.setOrderDetailList(userOrderMapper.getUserOrderByOrderId(orderVO.getId()));
            }
        }
        return orderVOIPage;
    }

    //加入购物车(暂时废弃)
    public UserOrder insertUserOrder(UserOrder userOrder){
        //判断当前商品是否已经在购物车，如果是则更新数量
        if(userOrder.getId() != null){
            userOrderMapper.updateById(userOrder);
        }

        userOrder.setId(CommonUtil.getUUID32());
        userOrder.setStatus(0);
        userOrderMapper.insert(userOrder);
        return userOrder;
    }

    //提交订单
    public Order submitOrder(Order order,String userOrderJson,Integer shopStatus){
        //没有任何货品
        if(CommonUtil.StringIsEmpty(userOrderJson)){
            throw new MyException(MyEumException.NO_GOODS_IN_ORDER);
        }

        order.setCreateTime(new Date());
        order.setStatus(1);
        order.setId(CommonUtil.getUUID32());

        List<UserOrder> userOrderList = JSONArray.parseArray(userOrderJson,UserOrder.class);
        for(UserOrder userOrder:userOrderList){
            userOrder.setId(CommonUtil.getUUID32());
            userOrder.setStatus(1);
            userOrder.setOrderId(order.getId());
            //扣除相应的货品剩余数量
            Goods goods = goodsMapper.selectById(userOrder.getGoodsId());
            GoodsOption goodsOption = goodsOptionMapper.selectById(userOrder.getOptionId());
            if(goods == null || goods.getStatus() != 1){
                throw new MyException(MyEumException.THE_GOODS_IS_DOWN.getCode(),String.format(MyEumException.THE_GOODS_IS_DOWN.getMessage(),goods.getName()));
            }

            //判断当前时间是否已到
            if(goods.getEndTime() != null && goods.getEndTime().getTime() < new Date().getTime()){
                throw new MyException(MyEumException.GOODS_OPTION_DEL);
            }

            //判断收货地址是否已经通过审核 3白名单 1通过
            if(shopStatus != 3 && shopStatus != 1){
                throw new MyException(MyEumException.ADDRESS_NOT_AUDIT);
            }

            //判断当前用户购买的单品是否有数量限制
            if(goodsOption.getPersonBuyLimit() == null){
                goodsOption.setPersonBuyLimit(0);
            }
            if(goodsOption.getPersonBuyLimit() != 0 && shopStatus != 3){
                Integer personCount = userOrderMapper.getPersonOrderCount(order.getOpenId(),userOrder.getOptionId());
                if(personCount + userOrder.getGoodsNum() > goodsOption.getPersonBuyLimit()){
                    throw new MyException(MyEumException.PERSON_BUY_GOODS_OUT_LIMIT.getCode(),String.format(MyEumException.PERSON_BUY_GOODS_OUT_LIMIT.getMessage(),goods.getName(),goodsOption.getPersonBuyLimit()));
                }
            }

            userOrder.setDiscountPrice(goodsOption.getDiscountPrice());
            userOrder.setHeadPic(goods.getHeadPic());
            userOrder.setPrice(goodsOption.getPrice());
            userOrder.setUnit(goods.getUnit());
//            goods.setSurplusNum(goods.getSurplusNum() - userOrder.getGoodsNum());
            goodsOption.setSurplusNum(goodsOption.getSurplusNum() - userOrder.getGoodsNum());
            goodsOption.setSellNum(goodsOption.getSellNum() + userOrder.getGoodsNum());
            if(goodsOption.getSurplusNum() < 0){
                throw new MyException(MyEumException.GOODS_NUM_NOT_ENOUGH.getCode(),String.format(MyEumException.GOODS_NUM_NOT_ENOUGH.getMessage(),userOrder.getGoodsName() + "-" + userOrder.getOptionTitle()));
            }
            goodsMapper.updateById(goods);
            goodsOptionMapper.updateById(goodsOption);
            userOrderMapper.insert(userOrder);
        }

        //添加订单
        orderMapper.insert(order);
        return order;
    }

    public Order cancelOrder(Order order){
        Order historyOrder = orderMapper.selectById(order.getId());
        if(historyOrder.getCreateTime().getTime() + 60*60*1000 < new Date().getTime()){
            throw new MyException(MyEumException.TIME_OUT_CANCEL);
        }
        historyOrder.setStatus(0);
        //获取该订单的所有商品
        List<UserOrderVO> userOrderVOList = userOrderMapper.getUserOrderByOrderId(order.getId());

        for(UserOrderVO userOrderVO : userOrderVOList){
            GoodsOption goodsOption = goodsOptionMapper.selectById(userOrderVO.getOptionId());
            if(goodsOption == null){
                continue;
            }
            goodsOption.setSellNum(goodsOption.getSellNum() - userOrderVO.getGoodsNum());
            goodsOption.setSurplusNum(goodsOption.getSurplusNum() + userOrderVO.getGoodsNum());
            goodsOptionMapper.updateById(goodsOption);
        }
        orderMapper.updateById(historyOrder);
        return order;

    }

}
