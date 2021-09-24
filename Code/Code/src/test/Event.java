package test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Event implements IEventRule {
    @Override
    public Void deal(Map<String, String> map) {
       return null;
    }

    public Function<Map<String, String>, Boolean> choice() {
        return (varMap)->{
            return true;
        };
    }
}
