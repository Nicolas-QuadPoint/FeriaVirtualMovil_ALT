package com.feriantes4dawin.feriavirtualmovil.ui.auction;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.util.SimpleAction;
import com.feriantes4dawin.feriavirtualmovil.ui.widgets.YesNoDialog;

public class PushTransportistaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_transportista);


        Button btnPujar = findViewById(R.id.tbpm_btnPujar);
        Button btnEliminar = findViewById(R.id.tbpm_btnEliminar);
        Button btnCancelar = findViewById(R.id.tbpm_btnCancelar);


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YesNoDialog ynd = new YesNoDialog(
                        PushTransportistaActivity.this,
                        getString(R.string.action_confirm),
                        getString(R.string.confirm_delete_push),
                        new SimpleAction() {
                            @Override
                            public void doAction(Object o) {

                                Toast.makeText(PushTransportistaActivity.this,"Debería hacer algo aquí!",Toast.LENGTH_SHORT).show();

                            }
                        },null);


            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}