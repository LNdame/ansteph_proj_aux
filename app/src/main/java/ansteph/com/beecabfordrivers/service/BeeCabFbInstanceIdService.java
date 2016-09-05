package ansteph.com.beecabfordrivers.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by loicStephan on 04/08/16.
 */
public class BeeCabFbInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token  = FirebaseInstanceId.getInstance().getToken();


    }

    private void registerToken(String token){

    }
}
