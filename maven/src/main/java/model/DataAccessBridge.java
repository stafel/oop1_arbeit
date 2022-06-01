package model;

/* Singleton acts as an abstraction layer between the controller and data handling.
because at this stage of development I am unsure if a serialization into files sufices or a db access is needed */
public class DataAccessBridge {
    private static DataAccessBridge bridge;

    private DataAccessBridge() {

    }

    public static DataAccessBridge getInstance() {
        if (bridge == null) {
            bridge = new DataAccessBridge();
        }
        return bridge;
    }
}
