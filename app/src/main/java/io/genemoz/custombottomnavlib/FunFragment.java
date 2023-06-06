package io.genemoz.custombottomnavlib;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import io.genemoz.custombottomnavlib.databinding.FragmentChatBinding;
import io.genemoz.custombottomnavlib.databinding.FragmentFunBinding;


public class FunFragment extends Fragment {

    FragmentFunBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFunBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}