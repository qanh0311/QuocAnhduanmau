package com.example.asm.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.R;
import com.example.asm.SQLite.Dao.LoaiSachDao;
import com.example.asm.SQLite.model.LoaiSach;
import com.example.asm.ui.home.loaiSach.loaiSachFragment;

import java.util.List;

public class Reciview_Adapter_LoaiSach extends RecyclerView.Adapter<Reciview_Adapter_LoaiSach.viewHolder> {
    LoaiSachDao loaiSachDao;
    Context context;
    List<LoaiSach> list;
    loaiSachFragment loaiSachFragment;
    Dialog dialog;
    EditText eTMaLS,etTenLS;
    Button btnSave,btnCancel;
    LoaiSach loaiSach;

    public Reciview_Adapter_LoaiSach(Context context, List<LoaiSach> list, loaiSachFragment loaiSachFragment) {
        this.context = context;
        this.list = list;
        this.loaiSachFragment = loaiSachFragment;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_loaisach,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        loaiSachDao = new LoaiSachDao(context);
        dialog = new Dialog(context);
        loaiSach = new LoaiSach();
        if(list != null){
            holder.tvMaLoaiSach.setText("Mã loại sách : "+list.get(position).getMaLoaiSach());
            holder.tvTenLoaiSach.setText("Tên loại sách : "+list.get(position).getTenLoaiSach());
            holder.iVDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete")
                            .setMessage("Bạn muốn xóa")
                            .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(loaiSachDao.delete(String.valueOf(list.get(holder.getAdapterPosition()).getMaLoaiSach())) > 0){
                                        Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                        loaiSachFragment.setData();
                                        dialogInterface.dismiss();
                                    }else {
                                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
            holder.iVSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.setContentView(R.layout.item_dialog_loaisach);
                    eTMaLS = dialog.findViewById(R.id.etMaLoaiSachDialog);
                    etTenLS = dialog.findViewById(R.id.etTenLoaiSachDialog);
                    btnSave = dialog.findViewById(R.id.btnSaveLS);
                    btnCancel = dialog.findViewById(R.id.btnCancelLS);
                    // do du lieu len edittext
                    eTMaLS.setEnabled(false);
                    eTMaLS.setText(String.valueOf(list.get(holder.getAdapterPosition()).getMaLoaiSach()));
                    etTenLS.setText(list.get(holder.getAdapterPosition()).getTenLoaiSach());
                    // xu li

                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(validate() > 0){
                                loaiSach.setMaLoaiSach(Integer.parseInt(eTMaLS.getText().toString()));
                                loaiSach.setTenLoaiSach(etTenLS.getText().toString());
                                if(loaiSachDao.updata(loaiSach) > 0){
                                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();

                                }
                            }
                            loaiSachFragment.setData();
                            dialog.dismiss();
                        }
                    });

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });
        }
    }

    public int validate(){
        if(eTMaLS.getText().toString().isEmpty() || etTenLS.getText().toString().isEmpty()){
            Toast.makeText(context, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView tvMaLoaiSach,tvTenLoaiSach;
        ImageView iVDel,iVSua;
        public viewHolder(@NonNull View view) {
            super(view);

            tvMaLoaiSach = view.findViewById(R.id.tvMaLoaiSach);
            tvTenLoaiSach = view.findViewById(R.id.tvTenLoaiSach);
            iVDel = view.findViewById(R.id.iVDeleteLS);
            iVSua = view.findViewById(R.id.iVUpdateLS);
        }
    }
}
