package com.jantoniodomga.macropay.Main.UI.ui.home;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.jantoniodomga.macropay.Contracts.IResultable;
import com.jantoniodomga.macropay.Entitys.LoginResponse;
import com.jantoniodomga.macropay.Entitys.Session;
import com.jantoniodomga.macropay.Task.BarCodeGenerator;
import com.jantoniodomga.macropay.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            homeViewModel.setContext(getContext());
        }
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        final ImageView barcode=binding.imgCodeBar;
        homeViewModel.getCode().observe(getViewLifecycleOwner(),barcode::setImageBitmap);
        homeViewModel.getJwt().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String jwt) {
                LoginResponse loginResponse=new LoginResponse().fromJson(jwt);
                String codeJWT[]=loginResponse.getToken().split("\\.");
                new BarCodeGenerator(codeJWT[0].toString(), new IResultable<Bitmap>() {
                    @Override
                    public void onSuccess(Bitmap result) {
                        barcode.setImageBitmap(result);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }).start();
            }
        });

        homeViewModel.getSession().observe(getViewLifecycleOwner(), new Observer<Session>() {
            @Override
            public void onChanged(Session session) {
                textView.setText(session.getEmail());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}