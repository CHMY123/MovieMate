package cn.edu.scnu.moviemate.service.impl;

import cn.edu.scnu.moviemate.service.PaymentService;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private AlipayClient alipayClient;
    
    @Value("${alipay.notify-url}")
    private String notifyUrl;
    
    @Value("${alipay.return-url}")
    private String returnUrl;
    
    private static final double VIP_PRICE_PER_MONTH = 19.9;

    @Override
    public String createVipOrder(Long userId, Integer months) {
        try {
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            request.setNotifyUrl(notifyUrl);
            request.setReturnUrl(returnUrl);
            
            // 生成订单号
            String orderNo = generateOrderNo(userId);
            
            // 计算总金额
            double totalAmount = VIP_PRICE_PER_MONTH * months;
            
            // 构建订单信息
            String bizContent = String.format(
                "{\"out_trade_no\":\"%s\"," +
                "\"total_amount\":\"%.2f\"," +
                "\"subject\":\"MovieMate VIP会员 %d个月\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}",
                orderNo, totalAmount, months
            );
            
            request.setBizContent(bizContent);
            
            // 调用支付宝接口
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                return response.getBody();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean handleAlipayNotify(Map<String, String> params) {
        // TODO: 验证签名
        // TODO: 验证订单金额
        // TODO: 更新用户VIP状态
        return true;
    }

    @Override
    public boolean handleAlipayReturn(Map<String, String> params) {
        // TODO: 验证签名
        // TODO: 验证订单状态
        return true;
    }

    @Override
    public String queryOrderStatus(String orderNo) {
        // TODO: 调用支付宝订单查询接口
        return "SUCCESS";
    }
    
    private String generateOrderNo(Long userId) {
        return String.format("%s%06d%04d",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
            userId,
            (int)(Math.random() * 10000)
        );
    }
} 