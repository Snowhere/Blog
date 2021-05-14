package rule;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptEngineTest {
    public static void main(String[] args) throws Exception {
        //创建引擎
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        //执行语句
        String scriptStr = "c=a-b;d=a+b";
        //传入条件变量
        engine.put("a", 1);
        engine.put("b", 2);
        //执行
        engine.eval(scriptStr);
        //获取结果变量
        System.out.println(engine.get("c"));
        System.out.println(engine.get("d"));
    }
}
