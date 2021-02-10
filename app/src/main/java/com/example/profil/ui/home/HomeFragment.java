package com.example.profil.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.profil.JSONParser;
import com.example.profil.Profil;
import com.example.profil.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

   Button btntelecharger;
   ListView lv;
   ArrayList<Profil> data =new ArrayList<Profil>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btntelecharger=root.findViewById(R.id.btntelecharger_home);
        lv=root.findViewById(R.id.lv_home);
        btntelecharger.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //lancer un thread de telechargement
        /**  extends thread/implements Runnable
         * extends Asynctask **/
        Telechargement t =new Telechargement(HomeFragment.this.getActivity());
        t.execute();
    }
});
        return root;
    }

    class Telechargement extends AsyncTask{
        Context con;
        AlertDialog alert;
        public Telechargement(Context con) {
            this.con = con;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //uithread
            AlertDialog.Builder dialog= new AlertDialog.Builder(con);
            dialog.setTitle("Telechargement");
            dialog.setMessage("Veuillez patientez...");

            alert=dialog.create();
            alert.show();
        }



        @Override
        protected Object doInBackground(Object[] objects) {
            //== run : 2eme processus
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

          /*  btntelecharger.setText("50% Telechargé..");
            //==> erreur access au btn
            //solution : executer cette instruction dans l'UIT
            */
          publishProgress(1) ; //appel vers onprogressupdat


             /*reseau local
        10.0.2.2 :AVD
        nom liste : hebergement
            */
             String ip ="192.168.43.34";
          String url ="http://"+ip+"/dashboard/servicephp/get_all_user.php";
            JSONObject response = JSONParser.makeRequest(url);

            try {
                int s = response.getInt("success");
                if (s==0){
                    String msg = response.getString("message");

                }
                else {
                    JSONArray tab=response.getJSONArray("profil");
                    for (int i=0;i<tab.length();i++){
                        JSONObject ligne =tab.getJSONObject(i);
                        String n =ligne.getString("nom");
                        String p =ligne.getString("prenom");
                        String ps =ligne.getString("pseudo");
                        data.add(new Profil(n,p,ps));

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

          /*  btntelecharger.setBackgroundColor(Color.GREEN);
            btntelecharger.setText("100% Terminé.");
            //==> erreur*/

            publishProgress(2) ;
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) { btntelecharger.setText("50% Telechargé..");

               super.onProgressUpdate(values);
            //uithread
            if (values[0]==(Object)1){
                btntelecharger.setText("50% Telechargé..");

            }
            else {
                btntelecharger.setBackgroundColor(Color.GREEN);
                btntelecharger.setText("100% Terminé.");
            }


        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //uithread
            alert.dismiss();
            ArrayAdapter ad =new ArrayAdapter(con,
                    android.R.layout.simple_list_item_1,
                    data);
            lv.setAdapter(ad);
        }
    }
}