public class ShadeMain {
    public static void main(String[] args) {
        // Fat Jar fails if built with direct reference to MainApp extends Application
        MainApp.main(args);
    }
}
