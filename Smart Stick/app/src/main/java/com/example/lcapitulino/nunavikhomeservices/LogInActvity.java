package com.example.lcapitulino.nunavikhomeservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogInActvity extends AppCompatActivity {
    private TextView Info;
    private Button Login;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);
    }

    public void onClickLogin2(View view) {
        EditText editTextUserName = (EditText) findViewById(R.id.loginUsername);
        String userName = editTextUserName.getText().toString();
        EditText editTextPass = (EditText) findViewById(R.id.loginPassword);
        String password = editTextPass.getText().toString();

        HomeService hService = HomeService.getInstance();
        User user = hService.findUser(userName);

        if (user == null) {
            MyToast.display(getApplicationContext(), "user does not exist");
            return;
        }

        if (!user.passEquals(password)) {
            MyToast.display(getApplicationContext(), "bad password");
            counter--;

            Info.setText("No of attempts remaining: " + String.valueOf(counter));
            if(counter == 0){
                Login.setEnabled(false);
            }
            return;
        }

        Intent intent = null;
        if (user.userEquals("admin")) {
            intent = new Intent(getApplicationContext(), AdminActivity.class);
        } else if (user.getRole() == User.Role.PROVIDER) {
            intent = new Intent(getApplicationContext(), ProviderActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), HomeOwnerActivity.class);
        }

        intent.putExtra("username", user.getUserName());
        startActivity(intent);
    }
}
