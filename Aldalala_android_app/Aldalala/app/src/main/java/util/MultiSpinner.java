package util;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import techline.carsapp.R;

/**
 * Created by Rajesh on 2017-08-11.
 */

public class MultiSpinner extends Spinner implements
        DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {

    private List<String> items;
    private boolean[] selected;
    private String defaultText;
    private MultiSpinnerListener listener;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked)
            selected[which] = true;
        else
            selected[which] = false;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // refresh text on spinner
        StringBuffer spinnerBuffer = new StringBuffer();
        boolean someUnselected = false;
        for (int i = 0; i < items.size(); i++) {
            if (selected[i] == true) {
                spinnerBuffer.append(","+items.get(i));
                //spinnerBuffer.append(", ");
            } else {
                someUnselected = true;
            }
        }

        String spinnerText = "";
        if (spinnerBuffer.length() == 0) {
            spinnerText = defaultText;
        } else {
            spinnerText = spinnerBuffer.substring(1);
        }

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[] { spinnerText });*/

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.row_spinner_text, R.id.tv_sp, new String[]{spinnerText});
        setAdapter(adapter);

        listener.onItemsSelected(selected);
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.MyAlertDialogTheme));
        builder.setMultiChoiceItems(
                items.toArray(new CharSequence[items.size()]), selected, this);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }

    public void setItems(List<String> items, String allText,
                         MultiSpinnerListener listener) {
        this.items = items;
        this.defaultText = allText;
        this.listener = listener;

        // all selected by default
        selected = new boolean[items.size()];

        for (int i = 0; i < selected.length; i++) {
            selected[i] = false;
        }

        // all text on the spinner
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, new String[] { allText });*/

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.row_spinner_text, R.id.tv_sp, new String[]{allText});
        setAdapter(adapter);
    }

    public interface MultiSpinnerListener {
        public void onItemsSelected(boolean[] selected);
    }

    public void setMultiSelection(List<String> items_id, List<String> selected_items_id) {

        StringBuffer spinnerBuffer = new StringBuffer();


        for (int j = 0; j < items_id.size(); j++) {

            int po = selected_items_id.indexOf(items_id.get(j));

            if (po >= 0) {
                selected[j] = true;

                spinnerBuffer.append(","+items.get(j));
                //spinnerBuffer.append(", ");

                Log.e("DATA", "true" + items.get(j));
            }
        }

        String spinnerText = "";
        if (spinnerBuffer.length() == 0) {
            spinnerText = defaultText;
        } else {
            spinnerText = spinnerBuffer.substring(1);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.row_spinner_text, R.id.tv_sp, new String[]{spinnerText});
        setAdapter(adapter);

        listener.onItemsSelected(selected);
    }

}