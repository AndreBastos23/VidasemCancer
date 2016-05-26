package com.vsc.vidasemcancer.Activities;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.vsc.vidasemcancer.R;
import com.vsc.vidasemcancer.RestOperation;
import com.vsc.vidasemcancer.entities.User;

import java.util.List;

public class ItemsListActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new RequestItemsServiceTask().execute();
    }

    /**
     * populate list in background while showing progress dialog.
     */
    private class RequestItemsServiceTask
            extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog =
                new ProgressDialog(ItemsListActivity.this);
        private List<User> itemsList;

        @Override
        protected void onPreExecute() {
            // TODO i18n
            dialog.setMessage("Please wait..");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {
            // The ItemService would contain the method showed
            // in the previous paragraph
            RestOperation itemService = new RestOperation();
            try {
                itemsList = itemService.findAllItems();
            } catch (Throwable e) {
                // handle exceptions
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            // setListAdapter must not be called at doInBackground()
            // since it would be executed in separate Thread
            setListAdapter(
                    new ArrayAdapter<>(ItemsListActivity.this,
                            R.layout.activity_item_list, R.id.teste_list_items, itemsList));

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
