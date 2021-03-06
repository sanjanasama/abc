package test;

// test parameter non-null inference
public class Test04 {

  void test1(
      Test04 p1,  // @Null: assigned a null value
      Test04 p2) { // @Non-null: only assigned non-null values
    test1(null, new Test04());
  }

  void test2(Test04 p1) { // @Non-null for resursive application
    test2(p1);
  }

  void test3(Test04 p1) { // @Non-null: non-null value + recursive
    test3(p1);
    test3(new Test04());
  }

  void test4(Test04 p1) { // @Null: null value + recursive
    test4(p1);
    test4(null);
  }

  // nullness on parameters is invariant for subtyping
  
  // subtyping through subclassing
  void test5(Test04 p1, // @Non-null: all assignments are non-null
             Test04 p2, // @Null: null value locally
             Test04 p3) { // @Null: null value in subclass
    test5(new Test04(), null, new Test04());
  }
  static class SubTest04 extends Test04 {
    void test5(Test04 p1, // @Non-null: all assignments are non-null
               Test04 p2, // @Null: null value in superclass
               Test04 p3) { // @Null: null value locally
      test5(new Test04(), new Test04(), null);
    }
  }

  // subtyping through interfaces
  interface I6 {
    void test6(Test04 p1, // @Non-null: all assignments are non-null
               Test04 p2, // @Null: null value in X6
               Test04 p3, // @Null: null value in Y6
               Test04 p4); // @Null: null value through I6
  }
  static class X6 implements I6 {
    public void test6(Test04 p1, // @Non-null: all assignments are non-null
               Test04 p2, // @Null: null value in X6
               Test04 p3, // @Null: null value in Y6
               Test04 p4) { // @Null: null value through I6
      new X6().test6(new Test04(), null, new Test04(), new Test04());
      ((I6)null).test6(new Test04(), new Test04(), new Test04(), null);
    }
  }
  static class Y6 implements I6 {
    public void test6(Test04 p1, // @Non-null: all assignments are non-null
               Test04 p2, // @Null: null value in X6
               Test04 p3, // @Null: null value in Y6
               Test04 p4) { // @Null: null value through I6
      new Y6().test6(new Test04(), new Test04(), null, new Test04());
    }
  }

  void test7() {
    try {
      throw new Exception();
    }
    catch(Exception e) { // @Non-null: all thrown exceptions are bound
    }
  }

  void test8() {
    for(String s : new java.util.ArrayList<String>()) { // @Null: iterator may return null value
    }
  }
}
