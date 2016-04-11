package cr.ac.itcr.examenict;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import DB.IBD;
import DB.UserRepository;

public class Login extends AppCompatActivity {

    Button btnLogin;
    TextView txtuser;
    TextView txtpass;
    String Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtuser = (TextView)findViewById(R.id.txtEmail);
        txtpass = (TextView)findViewById(R.id.txtPassword);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(txtuser.getText().toString().equals("") || txtpass.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Complete the Spaces",Toast.LENGTH_SHORT).show();
                }
                else{
                    UserRepository repository = new UserRepository(getApplicationContext());
                    Pass = repository.getPass(txtuser.getText().toString());
                    if(txtpass.getText().toString().equals(Pass)){
                        txtpass.setText("");
                        txtuser.setText("");
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"User or Passwword incorrect!",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
