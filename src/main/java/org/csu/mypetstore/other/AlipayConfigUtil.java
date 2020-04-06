package org.csu.mypetstore.other;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class AlipayConfigUtil {
    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id="2016102400751952";//例：2016082600317257
    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCpNgRKrxDShCBKaqevfgeOfkYGBpWsw8DkED7tUDg66wErNC16ZaL+G5XKvVBuk6jYGR5byOl1lGV/eYfvIepllQWJXHOyJNXHsZpWylqLqn+GwRiHTyIJUZ18mSzPgJhPu7YHlBUXmFQ9sLZhiiYCdGAy162Qg4lWFaWZn9+rw+mPFNxh0XmR0aTWjoQ3K1POmpYYxZfmTww+Og+jLs4dkXALuESn6bSnHsLgZ/sC+fqj6Irz+5tzo/r3jtLMxtZtUe6414p68oxZC173hkavpQSbV/UNJPB7WMX+gjRq9NjOXOs/iQlziTBDxu4SfLLR8OUXDg0/C/A8rw+1pfcbAgMBAAECggEAWZJiizZI7WdLwJjc0yUf0uo9Hwt1R9MH1LJU3ULNwUKNh9VDDbunoGPx0zSL0z0O7/W8AFJGYxt+7w/iSU/myIcuOxxZkj+1etQlIzRS4gQLJcYpudbF6bDucPqUofMsrWhsX26gC7FzRoS4xsFehYYdNky9b/ZxSMSEuOHMgRuBOqV+Vip3VgPHtQd2LydEEltRwrcvhE/vPU2kpr+cx6p+Yh3ZvdPZqvik8QuLP8LxVuI+Llh+4flFo5RyumZ4uMx1aYngjDQzng5I3y2mWspqCTie/MzffBz12q38ZHbpN/bpeRHuLE/SgZAEqDS7OLrgw0fmt3+Su8bPv4mfYQKBgQDx550rsAY+i683lRDV18VoQzPlaP+YBxmZb1CClHG0M/Ycnjc0ZOPVPuW2mbY/giht2KFo25+FU90o+fOhp8/gD4dLmHEVOzVYBA2XPt7t4rKWFkDzxxKrM6HeXxzXMW04vP0vCx/BYiUhiThEVIbysDntEbdiXDtaoICR6bwkDwKBgQCzEg/9eG/GscCQycbM37KFq3YCMn8sCGsdDXO8EzkqCLkQVNOI5lQB81kxRyIo/ImFSIVn7YLNs3LRbG0/JNZ4gwDuliX6S7eoFKFmc808OZfPCvV45SsfKqMVnNREFngHJi6yb01Gi8hdWf0obUexka+yWFXL+pan4sZM3qWANQKBgQDhdQYWhqt7NcMroaIcPEs/norKwO6fKdbhG09FNMc4tckm6Qr4qSNzR3jflCm4zT2TC3l8el0V7ZDNtRAt1XK4GKD3gu+6YwGXs8da9Hzf6A6cNcd5rUuoBqpR0AHYSrfKTtIGO27wVOHYfMTVYpaXsEl8u6tYYC/tinPqCpeQXwKBgFb8EC5lQY64K71pdptwkoyiBHn+w43FLRr4vN6ATAViJVuMiRD4KZik+A+a6ToVqkvHNZ6NXjvDzqNyUwMYRuJVaK982kfiXf1UwC4/VFPL+jVFsoYedDr4gC3wd5/3diiq4IYm0NfS8Op/2xy3MNtN1JYCmPKz8FjvLLqMcMJ9AoGBAIdt7XlbGDamJZ9y0VvmlMZ7MMS0DRnVXWieEm32gwofHBXt9LjXOMYenZgN7BrtRD27NFNjkgdJPNC82DA9AllWU9O03dm9KH81nvAWH29WdZaETXtbj0kt4J9xYuAszQow7Jh4Oj0svub8cNu2i/7ktGwVg6oblWF+iic3jEnB";// 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy6MTP7oCy3A7Bl89IQwybd77Kg7D3Ed46oZoqL8AlooWs5sP+v26GK9ZUlE5M3qyqU5KjLsqIfm1wkGxsEvCm441wcyl9JtFs6rk5sTRmVRbeSnI02EbIWIKyyn8ICt+R3FuphF3QGnKszgFH9rpf7YfxWQRrYA/eGEv2+znyiTUEwQAmxq62rxIwMDPA9pyRgJY9jvmM1EFi2Jwn1RSBKTmFhjy2RWCMNc6Gh9Xj6bk9UvX5WekldL36/3Wi89IHqZ91goY8YR9isZgvtqXz1Mz9qVUPjYYbF0mPeIZKPjheyqLAV+w4+je3gj4PSljSZUc6u8JyJ5enpb6jHEkbwIDAQAB";/**
     * 返回的时候此页面不会返回到用户页面，只会执行你写到控制器里的地址
     */
    public static String notify_url="http://30c7c19239.wicp.vip/alipay/notify_url";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    /**
     * 此页面是同步返回用户页面，也就是用户支付后看到的页面，上面的notify_url是异步返回商家操作，谢谢
     * 要是看不懂就找度娘，或者多读几遍，或者去看支付宝第三方接口API，不看API直接拿去就用，遇坑不怪别人,要使用外网能访问的ip,建议使用花生壳,内网穿透
     */
    public static String return_url = "http://30c7c19239.wicp.vip/alipay/aliCallback";
    // 签名方式
    public static String sign_type = "RSA2";
    // 字符编码格式
    public static String charset = "utf-8";
    // 支付宝网关
    public static String gatewayUrl="https://openapi.alipaydev.com/gateway.do";
    // 日志地址,这里在d盘下要创建这个文件,不然会报错
    public static String log_path = "D:/logs/";
    // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord
     *            要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_"
                    + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean checkSign(HttpServletRequest req) {
        Map<String, String[]> requestMap = req.getParameterMap();
        Map<String, String> paramsMap = new HashMap<> ();
        requestMap.forEach((key, values) -> {
            String strs = "";
            for(String value : values) {
                strs = strs + value;
            }
            System.out.println(("key值为"+key+"value为："+strs));
            paramsMap.put(key, strs);
        });

        //调用SDK验证签名
        try {
            return  AlipaySignature.rsaCheckV1(paramsMap, alipay_public_key,charset, sign_type);
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("*********************验签失败********************");
            return false;
        }
    }
    public static Map<String, String> turnToHash(HttpServletRequest req){
        Map<String, String[]> requestMap = req.getParameterMap();
        Map<String, String> paramsMap = new HashMap<> ();
        requestMap.forEach((key, values) -> {
            String strs = "";
            for(String value : values) {
                strs = strs + value;
            }
            System.out.println(("key值为"+key+"value为："+strs));
            paramsMap.put(key, strs);
        });
        return paramsMap;
    }

}

