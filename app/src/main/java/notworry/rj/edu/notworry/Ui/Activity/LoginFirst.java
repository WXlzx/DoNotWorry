package notworry.rj.edu.notworry.Ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import notworry.rj.edu.notworry.R;

public class LoginFirst extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_only_one);

        Button button = findViewById(R.id.jinruzhuyemian);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFirst.this, LoginMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}