public class MyException extends Exception {
    MyException(String msg) {
        super(msg);
    }

    public static void main(String[] args){
        throw new RuntimeException("1");
    }
}
