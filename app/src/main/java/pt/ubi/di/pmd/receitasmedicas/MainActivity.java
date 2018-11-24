package pt.ubi.di.pmd.receitasmedicas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private static final String Share_prefs ="sharedPrefs";
    private static final String Periodicidade ="horatoma";
    private static final String DataInicio ="datainicial";
    private static final String DataFinal ="datafinal";
    private static final String Proximatoma ="quantidadefalta";
    private static final String Pontotransicao = "butao";

    SimpleDateFormat dataformat= new SimpleDateFormat("dd/mm/yyyy");


    @Override
    protected void onStop() {
        super.onStop();
        //saveData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //getData();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botao1 = (Button) findViewById(R.id.butao1);

        botao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewPillDialog().show();
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    /*public void saveData(){
        SharedPreferences sharedPreferences= getSharedPreferences(Share_prefs,MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(Periodicidade,editar0.getText().toString());
        editor.putString(DataInicio,text8.getText().toString());
        editor.putString(DataFinal,editar2.getText().toString());
        editor.putString(Proximatoma,text7.getText().toString());
        editor.apply();

        }
        public void getData() {
            SharedPreferences sharedPreferences = getSharedPreferences(Share_prefs, MODE_PRIVATE);
            if (editar0 != null && editar2 != null && text8 != null && text7 != null) {
                editar0.setText(sharedPreferences.getString(Periodicidade, ""));
                text8.setText(sharedPreferences.getString(DataInicio, ""));
                editar2.setText(sharedPreferences.getString(DataFinal, ""));
                text7.setText(sharedPreferences.getString(Proximatoma, ""));
            }
        }
*/
     public void adicionarMed(String nome, Integer periodicidade, Integer nrTomas)    {
        PillModel pill = new PillModel(nome, periodicidade, nrTomas);
        LayoutInflater inflater = this.getLayoutInflater();
        View pillInfoView = inflater.inflate(R.layout.pill_info, null);

        View pillView = pill.fillPillView(pillInfoView.findViewById(R.id.pill_layout));
        LinearLayout meds = findViewById(R.id.meds);
        meds.addView(pillView);
    }


    AlertDialog createNewPillDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Novo Medicamento");
        LayoutInflater inflater = this.getLayoutInflater();
        final View newPillView = inflater.inflate(R.layout.new_pill, null);
        builder.setView(newPillView);

        builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String nome = ((EditText) newPillView.findViewById(R.id.med_nome_edit)).getText().toString();
                Integer periodicidade = Integer.parseInt(((EditText) newPillView.findViewById(R.id.med_perd_edit)).getText().toString());
                Integer nrTomas = Integer.parseInt(((EditText) newPillView.findViewById(R.id.med_tomas_edit)).getText().toString());

                adicionarMed(nome, periodicidade, nrTomas);
            }
        });
        DialogInterface.OnClickListener onClickListener = null;
        builder.setNegativeButton("Cancelar", onClickListener);
        return builder.create();
    }
    }




