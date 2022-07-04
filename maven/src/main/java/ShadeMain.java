public class ShadeMain {
    // just a proxy call, this 'shades' the MainApp from the outside to allow building of a fat JAR
    public static void main(String[] args) {
        MainApp.main(args);
    }
}
