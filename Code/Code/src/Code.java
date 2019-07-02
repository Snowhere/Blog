import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.apache.tools.ant.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Code {

    public static void main(String args[]) {
        stringObject();
    }


    public static void str() {
        String text = "aaaa {{s}} bbbb";
        String cccc = text.replace("{{ss}}", "cccc");
        System.out.println(cccc);
    }

    public static void code() {
        //核心加密算法，目标数字与 encryptKey 进行异或运算，一次运算加密，再一次运算解密
        long encryptKey = Long.parseLong("CAFEBABE", 16);//秘钥


        //原逻辑
        //产品包packageId不定长，validCode固定6位，provinceId固定2位，拼成一个长数字
        int packageId = 12345678, validCode = 666666, provinceId = 22;
        long packageAndCode = packageId * 100000000 + validCode * 100 + provinceId;
        //数字和 key 异或加密，然后转16进制
        String oldEncrypted = Long.toHexString(packageAndCode ^ encryptKey);
        System.out.println("原参数加密后：" + oldEncrypted);

        //新加额外逻辑
        //额外字符串参数 productNo 固定13位10进制数字
        String productNo = "0021806010022";
        Long data = Long.parseLong(productNo, 10);
        //数字和 key 异或加密，然后转16进制
        String productEncrypted = Long.toHexString(data ^ encryptKey);
        System.out.println("新参数产品包 productNo 加密后：" + productEncrypted);


        //两个加密参数用短线 "-" 拼接,作为 url 参数，传递方式不变 http://xxx.com?v=para
        String para = oldEncrypted + "-" + productEncrypted;
        System.out.println("url参数：" + para);

        //解密
        String[] split = para.split("-");

        //split[0]按原逻辑解密，解密过程略
        System.out.println("原参数解密略");

        //额外参数 productNo 解密
        long i = Long.parseLong(split[1], 16);
        long result = i ^ encryptKey;
        //填充至13位
        String str = String.format("%013d", result);
        System.out.println("新参数产品包 productNo 解密后：" + str);
    }

    public static void money() {
        String str = "20";
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");  // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        if (match.matches()) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
        if (str.contains(".")) {
            str = str.split("\\.")[0];
        }
        System.out.println(str);
    }

    public static void str1() {
        String x = "123";
        String y = "123";
        String z = String.valueOf(123);
        System.out.println(x==y); // true
        System.out.println(x==z); // false
        System.out.println(x.equals(y)); // true
        System.out.println(x.equals(z)); // true
    }

    public static void strIter() {
        String str = "1,2,3,4,5,6,7,8";
        ArrayList<String> packageIds = Lists.newArrayList(str.split(","));
        Iterator<String> iterator = packageIds.iterator();
        while (iterator.hasNext()) {
            int id = Integer.parseInt(iterator.next());
            if (id % 2 == 0) {
                iterator.remove();
            }
        }
        String newStr = org.apache.commons.lang3.StringUtils.join(packageIds, ",");

        System.out.println(packageIds);
        System.out.println(newStr);
    }

    @Getter
    @Setter
    static class TestDTO{
        String name;
        Integer sequence;
    }

    public static void lambda() {
        List<TestDTO> dtos = new ArrayList<>();


        List<String> nameList = dtos.stream().map(TestDTO::getName).collect(Collectors.toList());
    }

    public static void array() {
        int[][] dp = new int[10][10];
        System.out.println(dp[0][0]);
    }

    public static int ex() {
        try {
            System.out.println("start");
            //throw new RuntimeException("exception");
            //System.out.println("before try return");
            return 1;
            //System.out.println("after try return");
        } catch (Exception e) {
            System.out.println("before catch throw");
            //throw e;
            //System.out.println("after catch throw");
            return 2;
            //System.out.println("after catch return");
        }finally {
            System.out.println("before finally return");
            //return 3;
            //System.out.println("after finally return");
        }
    }

    public static void sort() {
        List<TestDTO> dtos = new ArrayList<>();
        TestDTO dto1 = new TestDTO();
        dto1.setSequence(1);
        TestDTO dto2 = new TestDTO();
        dto2.setSequence(2);
        dtos.add(dto1);
        dtos.add(dto2);
        Collections.sort(dtos, new Comparator<TestDTO>() {
            @Override
            public int compare(TestDTO o1, TestDTO o2) {
                return o2.getSequence()-o1.getSequence();
            }
        });

        System.out.println(dtos.get(0).getSequence());
    }

    public static void remove(){
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        for (String string : strings) {
            if ("2".equalsIgnoreCase(string)) {
                strings.remove(string);
            }
        }
    }

    public static void stringObject() {
        String s1="abc";
        String s2="abc";
        String s3 = new String("abc");
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
    }
}
