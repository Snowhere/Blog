package test;

public class Code {

	public static void main(String[] args) throws Exception{
		B b = new B();
		A a = new A();
		a.setString("abc");
		b.setA(a);
		System.out.println(b.getA().getString());
		A a1 = b.getA();
		a1.setString("qwer");
		System.out.println(b.getA().getString());
	}



}
class A {
	public String string = "123";

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
}
class B{
	public A a;

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}
}