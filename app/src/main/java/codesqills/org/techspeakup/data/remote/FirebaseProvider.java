package codesqills.org.techspeakup.data.remote;

/**
 * Created by kamalshree on 10/31/2018.
 */

public class FirebaseProvider {
    public static FirebaseHandler provide() {
        return new FirebaseHandlerImpl();
    }
}
