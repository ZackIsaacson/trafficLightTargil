public class SingletonController {
    private static SingletonController ourInstance = new SingletonController();

    public static SingletonController getInstance() {
        return ourInstance;
    }

    private SingletonController() {
    }
}
