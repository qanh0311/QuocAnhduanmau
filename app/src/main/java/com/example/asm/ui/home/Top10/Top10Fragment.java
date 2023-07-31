package com.example.asm.ui.home.Top10;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asm.Adapter.Receiview_Adapter_Top;
import com.example.asm.R;
import com.example.asm.SQLite.Dao.ThongKeDao;

public class Top10Fragment extends Fragment {
    RecyclerView recyclerView;
    ThongKeDao thongKeDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top10, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcvTop);
        thongKeDao = new ThongKeDao(getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        Receiview_Adapter_Top adapterTop = new Receiview_Adapter_Top(getActivity(),thongKeDao.getTop10());
        recyclerView.setAdapter(adapterTop);
    }
}