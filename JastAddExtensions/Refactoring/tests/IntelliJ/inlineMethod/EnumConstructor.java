enum EnumWithConstructor {
    Test("1"), Rest("2", "b");

    /*[*/EnumWithConstructor(String s) {
        this(s, "");
    }/*]*/

    EnumWithConstructor(String s, String s2) {
    }
}
