
public class Foo {

    public static void main(String[] args) {
	new Foo().foo();
    }
    public void foo() { }
}

aspect Bar {
    surround() : if(true) {

    } {}

  
}