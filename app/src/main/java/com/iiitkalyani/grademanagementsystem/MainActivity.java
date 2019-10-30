package com.iiitkalyani.grademanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    SharedPrefManager sharedPrefManager;
    private TextView main_fullname, main_email, roll_no;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private com.iiitkalyani.grademanagementsystem.StudentGradesAdapter gradesAdapter;
    private List<com.iiitkalyani.grademanagementsystem.StudentGrades> gradesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        Window window = getWindow();
        roll_no = findViewById(R.id.student_roll_main);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fetchGrades();
        sharedPrefManager = new SharedPrefManager(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View headerView =  navigationView.getHeaderView(0);
        main_fullname = headerView.findViewById(R.id.student_name);
        main_email = headerView.findViewById(R.id.student_email);
        if(sharedPrefManager.isLoggedIn()){
            User user = sharedPrefManager.getUser();
            main_fullname.setText(user.getName());
            main_email.setText(user.getEmail());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            sharedPrefManager.logout();
            Intent login_intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login_intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sem1) {
            Toast.makeText(getApplicationContext(), "Currently only semester 4's data is available to you",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_sem2) {
            Toast.makeText(getApplicationContext(), "Currently only semester 4's data is available to you",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_sem3) {
            Toast.makeText(getApplicationContext(), "Currently only semester 4's data is available to you",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_sem4) {
            Toast.makeText(getApplicationContext(), "Already on the Semester 4th page",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_sem5) {
            Toast.makeText(getApplicationContext(), "Currently only semester 4's data is available to you",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_sem6) {
            Toast.makeText(getApplicationContext(), "Currently only semester 4's data is available to you",Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_sem7) {
            Toast.makeText(getApplicationContext(), "Currently only semester 4's data is available to you",Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_sem8) {
            Toast.makeText(getApplicationContext(), "Currently only semester 4's data is available to you",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void fetchGrades() {
        //first getting the values
        final String regNo = ""+sharedPrefManager.getRegNo();
        class fetchGrades extends AsyncTask<Void, Void, String> {

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
                params.put("regno", regNo);
                return requestHandler.sendPostRequest(URLs.URL_GRADES, params);
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
                        JSONObject studentGrades = obj.getJSONObject("student_grades");
                        recyclerView = findViewById(R.id.recycler_view_grades);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        gradesList = new ArrayList<StudentGrades>();
                        gradesList.add(new StudentGrades("MA401", studentGrades.getString("MA401")));
                        gradesList.add(new StudentGrades("CS401", studentGrades.getString("CS401")));
                        gradesList.add(new StudentGrades("CS402", studentGrades.getString("CS402")));
                        gradesList.add(new StudentGrades("EC401", studentGrades.getString("EC401")));
                        gradesList.add(new StudentGrades("EC402", studentGrades.getString("EC402")));
                        gradesList.add(new StudentGrades("CS411", studentGrades.getString("CS411")));
                        gradesList.add(new StudentGrades("CS412", studentGrades.getString("CS412")));
                        gradesList.add(new StudentGrades("CS413", studentGrades.getString("CS413")));
                        gradesAdapter = new com.iiitkalyani.grademanagementsystem.StudentGradesAdapter(gradesList);
                        recyclerView.setAdapter(gradesAdapter);
                        gradesAdapter.notifyDataSetChanged();
                        roll_no.setText("39/CSE/"+studentGrades.getString("Roll")+"/"+regNo);
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

        fetchGrades ul = new fetchGrades();
        ul.execute();
    }
}
