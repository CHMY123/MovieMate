package cn.edu.scnu.moviemate.service;

import java.util.Map;

public interface PaymentService {
    // 创建VIP支付订单
    String createVipOrder(Long userId, Integer months);
    
    // 处理支付宝异步通知
    boolean handleAlipayNotify(Map<String, String> params);
    
    // 处理支付宝同步返回
    boolean handleAlipayReturn(Map<String, String> params);
    
    // 查询订单状态
    String queryOrderStatus(String orderNo);
}
