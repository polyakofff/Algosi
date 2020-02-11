package Samples.OOP;

public class Main {

    public static void main(String[] args) {
        MyObj mo = new MyObj(1);
        MyObj mo1 = new MyObj(2);
        mo.func(mo1);

        System.out.println(mo.getId());
        System.out.println(mo1.getId());
    }
}
