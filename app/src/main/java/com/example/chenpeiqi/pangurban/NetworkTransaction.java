package com.example.chenpeiqi.pangurban;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.content.LocalBroadcastManager;
import com.example.chenpeiqi.pangurban.MapConstruct.MapElement.MyMap;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

import static com.example.chenpeiqi.pangurban.Helper.MyLog.i;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NetworkTransaction extends JobIntentService {

    public static final int jobId_login_register = 0,
            jobId_check_daily_record = 1, jobId_input_daily_content = 2, test = 3;

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        i("NetworkTransaction.onHandleWork",intent);
        try {
            Socket socket = new Socket("10.0.2.2",13800);
            String action = intent.getAction();
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            JSONObject request = new JSONObject();
            request.put("id",intent.getStringExtra("id")).put("action",action);
            switch (action) {
                case "login":
                case "register":
                    request.put("password",intent.getStringExtra("password")); break;
                case "check daily record":
                case "daily input":
                    String[] date = new Date().toString().split(" ");
                    request.put("year",intent.getIntExtra("year",Integer.parseInt(date[5])))
                            .put("month",intent.getIntExtra("month",transMon(date[1])))
                            .put("date",intent.getIntExtra("date",Integer.parseInt(date[2])));
                    break;

            }
            i(1,"request",request);
            bw.write(request.toString());
            bw.flush();

            //todo:analyse reply

            BufferedReader br =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            JSONObject reply = new JSONObject(br.readLine());
            i(1,"reply",reply);
            Intent broadcast = new Intent(request.getString("action"));
            switch (reply.getString("action")) {
                case "check daily record":
                case "register"://register成功后在本地产生地图数据
                    if (reply.getBoolean("result")){
                        i(2,"about to new MyMap()");
                        broadcast.putExtra("map",new MyMap());
                    }
                case "login":
                    broadcast.putExtra("result",reply.getBoolean("result"));
                    broadcast.putExtra("id",reply.getString("id"));
                    break;
            }
            i("NetworkTransaction >> broadcast",broadcast.toString());
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private int transMon(String month) {
        switch (month) {
            case "Jan":
                return 1;
            case "Feb":
                return 2;
            case "Mar":
                return 3;
            case "Apr":
                return 4;
            case "May":
                return 5;
            case "Jun":
                return 6;
            case "Jul":
                return 7;
            case "Aug":
                return 8;
            case "Seq":
                return 9;
            case "Oct":
                return 10;
            case "Nov":
                return 11;
            case "Dec":
                return 12;
        }
        return 0;
    }
}
