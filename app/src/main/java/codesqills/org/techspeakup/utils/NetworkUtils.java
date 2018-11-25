package codesqills.org.techspeakup.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import codesqills.org.techspeakup.R;

/**
 * Created by kamalshree on 11/25/2018.
 */

public class NetworkUtils extends AsyncTask<String, Void, Integer> {
    Context context;
    private static final String TAG = "NetworkUtils";

    public NetworkUtils(Context context) {
        this.context = context;
    }


    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    protected Integer doInBackground(String... params) {

        Integer result = 0;
        try {
            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
            socket.connect(socketAddress, 1500);
            socket.close();
            result = 1;
        } catch (IOException e) {
            e.printStackTrace();
            result = 0;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (isConnected()) {
            if (result == 1) {
                Log.e(TAG, "Internet Available");
            }

            if (result == 0) {

                buildDialog(context).show();
            }
        } else {
            buildDialog(context).show();
        }
        super.onPostExecute(result);
    }

    private AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(c.getString(R.string.no_internet_title));
        builder.setMessage(c.getString(R.string.no_internet_message));

        builder.setPositiveButton(c.getString(R.string.no_interent_okbutton), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).finish();
            }

        });

        return builder;
    }
}
