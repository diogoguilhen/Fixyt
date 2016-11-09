package fixyt.fixyt;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Sergio on 09/11/2016.
 */

public class FireApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
