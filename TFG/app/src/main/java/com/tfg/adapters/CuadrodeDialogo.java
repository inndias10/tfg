package com.tfg.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import com.tfg.R;
import com.tfg.activities.MyCreateConversation;

public class CuadrodeDialogo extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.titulo);
        builder.setItems(R.array.tipos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent conversacion = new Intent(getContext(), MyCreateConversation.class);
                if (which == 0) {
                    privada(conversacion);
                } else if (which == 1) {
                    grupal(conversacion);
                }
            }
        });
        return builder.create();
    }

    private void privada(Intent privada) {
        privada.putExtra("tipo", "privada");
        startActivity(privada);
    }

    private void grupal(Intent grupal) {
        grupal.putExtra("tipo", "grupal");
        startActivity(grupal);
    }
}
