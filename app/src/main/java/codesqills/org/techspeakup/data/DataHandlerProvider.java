package codesqills.org.techspeakup.data;

/**
 * Created by kamalshree on 10/29/2018.
 */

public class DataHandlerProvider {
    public static DataHandler provide() {

        return AppDataHandler.getInstance();
    }
}
