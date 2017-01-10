package util;

public class MyArrayList<E> {

    private Object[] elements;

    /**
     * 默认的构造函数，初始化内部数组，默认大小为0
     */
    public MyArrayList() {
        elements = new Object[0];
    }

    /**
     * 根据下标获取元素
     */
    public E get(int index) {
        return (E) elements[index];
    }

    /**
     * 根据下标设置元素的值
     */
    public void set(int index, E element) {
        elements[index] = element;
    }

    /**
     * 根据下标移除元素，数组需要变小
     */
    public E remove(int index) {
        E element = get(index);
        Object[] oldElements = elements;
        elements = new Object[oldElements.length - 1];
        for (int i = 0; i < index; i++) {
            elements[i] = oldElements[i];
        }
        // 下标之后的所有元素向前移位
        for (int i = index; i < elements.length; i++) {
            elements[i] = oldElements[i + 1];
        }
        return element;
    }

    /**
     * 在队尾插入元素，数组需要扩容
     */
    public void add(E element) {
        Object[] oldElements = elements;
        // 建立新的更大的数组
        elements = new Object[oldElements.length + 1];
        // 拷贝
        for (int i = 0; i < oldElements.length; i++) {
            elements[i] = oldElements[i];
        }
        elements[oldElements.length] = element;
    }

    public void add(int index, E element) {
        Object[] oldElements = elements;
        elements = new Object[oldElements.length + 1];
        for (int i = 0; i < index; i++) {
            elements[i] = oldElements[i];
        }
        elements[index] = element;
        for (int i = index + 1; i <= oldElements.length; i++) {
            elements[i] = oldElements[i - 1];
        }
    }
}
