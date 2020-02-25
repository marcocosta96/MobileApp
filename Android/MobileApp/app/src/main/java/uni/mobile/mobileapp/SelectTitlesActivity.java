package uni.mobile.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SelectTitlesActivity extends AppCompatActivity {

    private final List<TitleModel> titles = new ArrayList<>();
    private CustomAdapter adapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_titles);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(TextRecognitionActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        listView = findViewById(R.id.listview);


        adapter = new CustomAdapter(this, titles);
        listView.setAdapter(adapter);


        String[] potentialTitles = message.split("\n");
        for (String t : potentialTitles) {
            if (t.length() > 5)
                titles.add(new TitleModel(t));
        }
 /*       titles.add(new TitleModel(false, "Divina Commedia"));
        titles.add(new TitleModel(false, "Se questo è un uomo"));
*/
        listView.setEmptyView(findViewById(R.id.emptyElement));
        /*
        for (int i = 0; i < listView.getChildCount(); i++) {
            View view = listView.getChildAt(i);
            EditText et = view.findViewById(R.id.entry_name);
            et.setBackgroundResource(android.R.color.transparent);
            et.setClickable(false);

        }*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TitleModel model = titles.get(i);
                model.setSelected(!model.isSelected());
                titles.set(i, model);

                //now update adapter
                adapter.updateRecords(titles);
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "TODO", Toast.LENGTH_LONG).show();
                EditText et=(EditText) view.findViewById(R.id.entry_name);
                et.setFocusable(true);

                return true;
            }
        });

    }

    public void setTitles(String newTitle) {
        if (!titles.contains(newTitle))
            titles.add(new TitleModel(false, newTitle));
        adapter.updateRecords(titles);

    }


    //On click select all titles
    public void onAllClick(View v) {
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            TitleModel model = titles.get(i);
            model.setSelected(true);
            titles.set(i, model);
        }
        adapter.updateRecords(titles);

    }

    // On click remove selection on all titles
    public void onNoneClick(View v) {
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            TitleModel model = titles.get(i);
            model.setSelected(false);
            titles.set(i, model);
        }
        adapter.updateRecords(titles);

    }

    //On click send data to rails backend
    public void onSendClick(View v) {
        //TODO send to rails app
        Toast.makeText(this, "Should send to rails", Toast.LENGTH_LONG).show();
    }
}
