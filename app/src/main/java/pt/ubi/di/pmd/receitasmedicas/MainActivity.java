package pt.ubi.di.pmd.receitasmedicas;

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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private static final String Share_prefs = "sharedPrefs";
    private static final String Periodicidade = "horatoma";
    private static final String DataInicio = "datainicial";
    private static final String DataFinal = "datafinal";
    private static final String Proximatoma = "quantidadefalta";
    private static final String Pontotransicao = "butao";

    private ArrayList<PillModel> pillList = new ArrayList<PillModel>();

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
        for (PillModel med : pillList) {
            mostarMed(med);
        }
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

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Share_prefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String serializado = gson.toJson(pillList);
        editor.putString("pills", serializado);
        editor.apply();
    }

    public void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Share_prefs, MODE_PRIVATE);
        Gson gson = new Gson();
        Type datasetListType = new TypeToken<ArrayList<PillModel>>() {
        }.getType();

        String json = sharedPreferences.getString("pills", "[]");
        pillList = gson.fromJson(json, datasetListType);

    }

    public void adicionarMed(String nome, Integer periodicidade, Integer nrTomas) {
        PillModel pill = new PillModel(nome, periodicidade, nrTomas);
        pillList.add(pill);
        mostarMed(pill);
    }

    private void mostarMed(final PillModel pill) {
        LayoutInflater inflater = this.getLayoutInflater();
        View pillInfoView = inflater.inflate(R.layout.pill_info, null);

        final View pillView = pill.fillPillView(pillInfoView.findViewById(R.id.pill_layout));
        LinearLayout meds = findViewById(R.id.meds);

        Button tomar = pillView.findViewById(R.id.tomar_med);

        tomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
                pill.tomar();
            }
        });
        meds.addView(pillView);
    }


    AlertDialog createNewPillDialog() {
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




