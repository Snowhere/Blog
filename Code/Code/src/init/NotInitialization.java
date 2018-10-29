package init;
/***
 * @Description:被动引用不会触发类的初始化
 * @author suntenghao
 * @date 2018-10-29 16:24
 */
public class NotInitialization {
    public static void main(String[] args) {
        //对于静态字段，只有直接定义这个字段的类会被初始化
        System.out.println(SuperClass.value);
        //生成数组类
        SuperClass[] sca = new SuperClass[10];
        //常量在编译阶段存入调用类的常量池
        System.out.println(ConstClass.HELLOWORLD);
    }
}
