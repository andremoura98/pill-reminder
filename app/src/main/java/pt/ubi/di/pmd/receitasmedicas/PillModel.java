package pt.ubi.di.pmd.receitasmedicas;

import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PillModel {
    private String nome;
    private Integer periodicidade;
    private Integer nrTomas;
    private Date ultimaToma = new Date();
    private Date proximaToma;
    private Integer tomasRestantes;

    private static DateFormat dataformat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public PillModel(String nome, Integer periodicidade, Integer nrTomas) {
        this.nome = nome;
        this.periodicidade = periodicidade;
        this.nrTomas = nrTomas;
        this.tomasRestantes = nrTomas;
        this.ultimaToma = new Date();

        this.tomar();
    }

    private void tomar() {
        this.ultimaToma = new Date();
        this.tomasRestantes--;

        GregorianCalendar x = new GregorianCalendar();
        x.setGregorianChange(this.ultimaToma);
        x.add(Calendar.HOUR_OF_DAY, this.periodicidade);

        this.proximaToma = x.getTime();
    }

    public View fillPillView(View pill) {
        ((TextView) pill.findViewById(R.id.med_nome)).setText(this.nome);
        ((TextView) pill.findViewById(R.id.ult_toma)).setText(dataformat.format(this.ultimaToma));
        ((TextView) pill.findViewById(R.id.proxima_toma)).setText(dataformat.format(this.proximaToma));
        ((TextView) pill.findViewById(R.id.tomas_rest)).setText(this.tomasRestantes.toString());

        return pill;
    }

    @Override
    public String toString() {
        return "PillModel{" +
                "nome='" + nome + '\'' +
                ", periodicidade=" + periodicidade +
                ", nrTomas=" + nrTomas +
                ", ultimaToma=" + ultimaToma +
                ", proximaToma=" + proximaToma +
                ", tomasRestantes=" + tomasRestantes +
                '}';
    }
}

