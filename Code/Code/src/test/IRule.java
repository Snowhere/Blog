package test;

import java.util.Map;

public interface IRule<T> {
    T deal(Map<String, String> map);
}
