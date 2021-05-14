package rule;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.JsonRuleDefinitionReader;
import org.mvel2.ParserContext;

import java.io.FileReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class EasyRuleTest {
    public static void main(String[] args) throws Exception{
        String ruleStr = "[{\n" +
                "    \"name\": \"3\",\n" +
                "    \"description\": \"3\",\n" +
                "    \"condition\": \"m.user<50\",\n" +
                "    \"priority\": 3,\n" +
                "    \"actions\": [\n" +
                "      \"System.out.println(\\\"hello world \\\")\",\n" +
                "      \"a=22\","+
                "     \"if(a>20){System.out.println(\\\"hello world \\\")}\""+
                "    ]\n" +
                "}]";
        System.out.println(ruleStr);
        // create rules
        MVELRuleFactory ruleFactory = new MVELRuleFactory(new JsonRuleDefinitionReader());
        ParserContext context =new ParserContext();
        Rule rule = ruleFactory.createRule(new StringReader(ruleStr));
        Rules rules = new Rules(rule);
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();

        Facts facts = new Facts();
        Map<String, Object> map = new HashMap<>();
        map.put("user", 50);
        facts.put("m", map);




        rulesEngine.fire(rules,facts);

        System.out.println(facts);

    }
}
