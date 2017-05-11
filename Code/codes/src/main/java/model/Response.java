package model;

import com.jfinal.plugin.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Response {

    public int value = 0;
    public List<First> list = new ArrayList<>();

    public First push(String firstName) {
        value++;
        for (First first : list) {
            if (first.getName().equals(firstName)) {
                return first;
            }
        }
        First first = new First(firstName);
        list.add(first);

        return first;
    }

    @Getter
    @Setter
    public static class First {
        public String name;
        public int value;
        public List<Second> list;

        public First(String name) {
            this.value = 0;
            this.name = name;
            this.list = new ArrayList<>();
        }

        public Second push(String secondName) {
            value++;
            for (Second second : list) {
                if (second.getName().equals(secondName)) {
                    return second;
                }
            }
            Second second = new Second(secondName);
            list.add(second);

            return second;
        }
    }

    @Getter
    @Setter
    public static class Second {
        public String name;
        public int value;
        public List<Third> list = new ArrayList<>();

        public Second(String name) {
            this.value = 0;
            this.name = name;
            this.list = new ArrayList<>();
        }

        public Third push(String thirdCode, String name) {
            value++;
            for (Third third : list) {
                if (third.getName().equals(name)) {
                    return third;
                }
            }
            Third third = new Third(thirdCode, name);
            list.add(third);

            return third;
        }
    }

    @Getter
    @Setter
    public static class Third {
        public String name;
        public String code;
        public List<Model<? extends Model<?>>> list;
        public int value;

        public Third(String code, String name) {
            this.value = 0;
            this.code = code;
            this.name = name;
            this.list = new ArrayList<>();
        }

        public void push(Model<? extends Model<?>> model) {
            list.add(model);
            value++;
        }
    }
}
