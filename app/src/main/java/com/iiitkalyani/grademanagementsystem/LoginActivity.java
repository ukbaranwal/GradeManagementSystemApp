package com.iiitkalyani.grademanagementsystem;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static View view;
    private static EditText emailid, password;
    private static Button loginButton;
    private static Animation shakeAnimation;
    private static ConstraintLayout loginLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initViews() {
        emailid = (EditText) findViewById(R.id.signin_email);
        password = (EditText) findViewById(R.id.signin_password);
        loginButton = (Button) findViewById(R.id.signin_button);
        // Load ShakeAnimation
        loginLayout = (ConstraintLayout) findViewById(R.id.login_layout);
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.shake);
        loginButton.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkValidation();
        }
    };

    private void checkValidation() {
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(com.iiitkalyani.grademanagementsystem.Utils.regEx);

        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            Toast.makeText(getApplicationContext(), "Enter Both Credentials", Toast.LENGTH_SHORT).show();

        }
        // Check if email id is valid or not
        else if (!m.find())
            Toast.makeText(getApplicationContext(), "Email Id Inavlid", Toast.LENGTH_SHORT).show();
        else {
            userLogin();
        }
    }

    private void userLogin() {
        //first getting the values
        final String user_email = emailid.getText().toString();
        final String user_password = password.getText().toString();

        class UserLogin extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                viewLoadingDialog.showDialog();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", user_email);
                params.put("password", user_password);
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                viewLoadingDialog.hideDialog();
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        //Toast.makeText(getActivity().getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getInt("batch"),
                                userJson.getString("name"),
                                user_email, userJson.getInt("regno")

                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent mainActivity = new Intent(getApplicationContext(), com.iiitkalyani.grademanagementsystem.MainActivity.class);
                        startActivity(mainActivity);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Error! Invalide username/password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }
}
