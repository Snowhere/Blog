package math;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Operator {
    PLUS("+", 1), SUB("-", 1), MULT("*", 2), DIV("/", 2), BRACKET_LEFT("(", 0), BRACKET_RIGHT(")", 0);

    private String sign;
    private int level;

    Operator(String sign, int level) {
        this.sign = sign;
        this.level = level;
    }

    private static final Map<String, Operator> MAP = new HashMap<>();
    static {
        for (Operator c : EnumSet.allOf(Operator.class)) {
            MAP.put(c.getSign(), c);
        }
    }

    public static Operator getOperator(String sign) {
        return MAP.get(sign);
    }

    public static boolean isOperator(String key) {
        return MAP.containsKey(key);
    }

    public boolean isLower(Operator operator) {
        return this.level > operator.getLevel();
    }

    public String getSign() {
        return sign;
    }

    public int getLevel() {
        return level;
    }
}
