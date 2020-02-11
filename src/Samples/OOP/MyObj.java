package Samples.OOP;

public class MyObj {
    private int id;

    public MyObj(int id) {
        this.id = id;
    }

    public void func(MyObj mo) {
        mo.id = 10;
    }

    public int getId() {
        return id;
    }
}
