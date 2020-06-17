package org.csu.mypetstore.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import com.alipay.api.request.AlipayTradePagePayRequest;

import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.other.AlipayConfigUtil;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@SessionAttributes({"order"})
@RequestMapping("/alipay")
public class AlipayIumpSum {

    @Autowired
    OrderService orderService;

    @Autowired
    CatalogService catalogService;

    @GetMapping("/pay")
    public Object alipayIumpSum(Model model, @SessionAttribute("order") Order order, HttpSession session, HttpServletResponse response)
            throws Exception {
        String WIDout_trade_no = order.getTotalPrice ().toString ();
        String WIDsubject = String.valueOf (order.getOrderId ());
        String WIDtotal_amount = order.getUsername ();
        String WIDbody = order.getOrderDate ().toString ();

        String payables = WIDout_trade_no;
        // 订单名称，必填(必须是数字)
        String subject = WIDsubject;
        // 付款金额，必填
        String total_fee = WIDtotal_amount;
        // 商品描述，可空
        String body = WIDbody;
        // 获得初始化的AlipayClient
        String sessionId = session.getId ();
        String passback_params2 =java.net.URLEncoder.encode(sessionId,"UTF-8");
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfigUtil.gatewayUrl, AlipayConfigUtil.app_id,
                AlipayConfigUtil.merchant_private_key, "json", AlipayConfigUtil.charset,
                AlipayConfigUtil.alipay_public_key, AlipayConfigUtil.sign_type);
        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfigUtil.return_url);
        alipayRequest.setNotifyUrl(AlipayConfigUtil.notify_url);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = sdf.format(new Date());
        // 付款金额，必填
        String total_amount = payables.replace(",", "");
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," +
                "\"total_amount\":\"" + total_amount + "\"," +
                "\"subject\":\"" + subject + "\"," +
                "\"body\":\"" + body + "\","+
                "\"passback_params\":\""+passback_params2+"\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        // 请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        // System.out.println(result);
        //EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        AlipayConfigUtil.logResult(result);// 记录支付日志
        response.setContentType("text/html; charset=gbk");
        PrintWriter out = response.getWriter();
        out.print(result);
        return null;
    }

    /*** 异步回调*/
    @ResponseBody
    @RequestMapping(value = "/notify_url", method = RequestMethod.POST)
    public String notify_url(HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException {

        // 一定要验签，防止黑客篡改参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder notifyBuild = new StringBuilder(">>>>>>>>>> alipay notify >>>>>>>>>>>>>>\n");
        parameterMap.forEach((key, value) -> notifyBuild.append(key + "=" + value[0] + "\n"));
        notifyBuild.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        boolean flag = AlipayConfigUtil.checkSign (request);

        if (flag) {
            Map<String,String> params = AlipayConfigUtil.turnToHash (request);
            String sessionid = new String(params.get("passback_params").getBytes("ISO-8859-1"),"UTF-8");


  /*          Account account = (Account)session.getAttribute ("account");
            Order order = (Order)session.getAttribute ("order");*/

            /**
             * TODO 需要严格按照如下描述校验通知数据的正确性
             *
             * 商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
             * 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
             * 同时需要校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
             *
             * 上述有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
             * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
             * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
             */


    /*        if (tradeStatus.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，
                // 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (tradeStatus.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，
                // 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。

            }*/
            return "success";
        }
        return "fail";
    }

    @GetMapping("/aliCallback")
    public String  aliCallback(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        if(AlipayConfigUtil.checkSign (request)) {
            return "order/successPay.html"; }
        else {
            return "order/failPay.html"; }
    }
}
