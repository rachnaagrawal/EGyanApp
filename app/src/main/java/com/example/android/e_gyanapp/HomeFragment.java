package com.example.android.e_gyanapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    SharedPreferences sharedpreferences;
    private DrawerLayout mDrawerLayout;
   private android.widget.ShareActionProvider mShareActionProvider;
   FloatingActionButton x;
   //database reference
    private DatabaseReference mDatabase;

    //recycler view
    private RecyclerView recyclerView;

    //progress dialog
   // private ProgressDialog progressDialog;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        setUpToolbar(view);

        //progressDialog = new ProgressDialog(getContext());

        //List of books
       final ArrayList<Upload> bookEntries = new ArrayList<Upload>();
        /*bookEntries.add(new BookEntry("How To Read A Book", "By Charles Van Doren", R.drawable.how_to_read_a_book));
        bookEntries.add(new BookEntry("The Marquis", "By Luara Aurichio", R.drawable.themarquis));
        bookEntries.add(new BookEntry("The Jungle Book", "By Rudyard Kipling", R.drawable.junglebook));*/

        //progressDialog.setMessage("Please wait...");
        //progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        // Set up the RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        // BookEntry.BookEntryList(getResources()));

        int largePadding = getResources().getDimensionPixelSize(R.dimen.book_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.book_grid_spacing_small);
        recyclerView.addItemDecoration(new BookGridItemDecoration(largePadding, smallPadding));

        mDatabase.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot snapshot) {
                                                //dismissing the progress dialog
                                                //progressDialog.dismiss();

                                                //iterating through all the values in database
                                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                                    Upload bookEntry = postSnapshot.getValue(Upload.class);
                                                    bookEntries.add(bookEntry);
                                                }
                                                //creating adapter
                                                BookCardRecyclerViewAdapter adapter = new BookCardRecyclerViewAdapter(getContext(), bookEntries);

                                                //adding adapter to recyclerview
                                                recyclerView.setAdapter(adapter);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

            x=(FloatingActionButton) view.findViewById(R.id.Fab);

            recyclerView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent=new Intent(getContext(),DetailActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
            );

x.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(getContext(),ImageActivity.class);
        startActivity(i);
    }
});


        return view;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.Appbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            ActionBar actionbar = activity.getSupportActionBar();
            assert actionbar != null;
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.navigation_drawer_icon);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDrawerLayout = getView().findViewById(R.id.drawer_layout);


        NavigationView navigationView = getView().findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home: {
                                HomeFragment home = new HomeFragment();
                                (getActivity()).getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.main_frame, home).commit();
                                break;
                            }
                            case R.id.nav_profile: {
                                (getActivity()).getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.main_frame, new ProfileFragment()).commit();
                                break;
                            }
                            case R.id.nav_shareapp: {
                                // Fetch and store ShareActionProvider
                                if(menuItem!=null){

                                    try {
                                        Intent i = new Intent(Intent.ACTION_SEND);
                                        i.setType("text/plain");
                                        i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                                        String sAux = "\nLet me recommend you this application\n\n";
                                        sAux = sAux + "https://play.google.com/store/apps/details?id=the.package.id \n\n";
                                        i.putExtra(Intent.EXTRA_TEXT, sAux);
                                        startActivity(Intent.createChooser(i, "choose one"));
                                    } catch(Exception e) {
                                        //e.toString();
                                    }
                                }


                                // Return true to display menu
                                return true;

                            }
                            case R.id.nav_feedback: {
                                (getActivity()).getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.main_frame, new FeedbackFragment()).commit();
                                break;
                            }
                            case R.id.nav_privacy: {
                                PrivacyPolicyFragment privacy = new PrivacyPolicyFragment();

                                (getActivity()).getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.main_frame, privacy).commit();
                                break;

                            }
                            case R.id.nav_aboutus:{
                                (getActivity()).getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.main_frame, new AboutUsFragment()).commit();
                                break;
                            }
                            case R.id.nav_logout:
                            {
                                sharedpreferences = getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.commit();
                                getActivity().finish();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);

                            }
                        }

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            mShareActionProvider.setShareIntent(shareIntent);

        }
    }
}
