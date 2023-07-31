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
import com.example.asm.SQLite.Dao.ThanhVienDao;
import com.example.asm.SQLite.model.ThanhVien;
import com.example.asm.ui.home.thanhVien.thanhVienFragment;

import java.util.ArrayList;
import java.util.List;

public class ReciView_Adapter_ThanhVien extends RecyclerView.Adapter<ReciView_Adapter_ThanhVien.ViewHolder> {
    Context context;
    List<ThanhVien> list;
    thanhVienFragment thanhVienFragment;
    Dialog dialog;
    EditText etMaTT,etTenTT,etNamSinh;
    Button btnSave,btnCancel;
    ThanhVien thanhVien;
    ThanhVienDao thanhVienDao;

    public ReciView_Adapter_ThanhVien(Context context, List<ThanhVien> list, thanhVienFragment thanhVienFragment) {
        this.context = context;
        this.list = list;
        this.thanhVienFragment = thanhVienFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_rcv_thanhvien,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       if(list != null){
           holder.tvMaTT.setText("Mã thành viên : "+list.get(position).getMaTV());
           holder.tvTenTT.setText("Tên thành viên : " + list.get(position).getHoTenTV());
           holder.tvNSTT.setText("Năm sinh : " +list.get(position).getNamSinh());
           holder.btnXoa.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   thanhVienFragment.xoa(String.valueOf(list.get(holder.getAdapterPosition()).getMaTV()));
               }
           });
           holder.btnSua.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   dialog = new Dialog(context);
                   dialog.setContentView(R.layout.item_dialog_thanhvien);
                   thanhVienDao = new ThanhVienDao(context);

                   etMaTT = dialog.findViewById(R.id.etMaTTDialog);
                   etTenTT = dialog.findViewById(R.id.etTenTTDialog);
                   etNamSinh = dialog.findViewById(R.id.etNamSinhDialog);
                   btnCancel = dialog.findViewById(R.id.btnCancell);
                   btnSave = dialog.findViewById(R.id.btnSavee);

                   etMaTT.setEnabled(false);
                   // hien thi len edittext
                   etMaTT.setText(String.valueOf(list.get(holder.getAdapterPosition()).getMaTV()));
                   etTenTT.setText(list.get(holder.getAdapterPosition()).getHoTenTV());
                   etNamSinh.setText(String.valueOf(list.get(holder.getAdapterPosition()).getNamSinh()));

                       // xu li
                   btnCancel.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           dialog.dismiss();
                       }
                   });

                   btnSave.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {

                           thanhVien = new ThanhVien();
                           thanhVien.setMaTV(Integer.parseInt(etMaTT.getText().toString()));
                           thanhVien.setHoTenTV(etTenTT.getText().toString());
                           thanhVien.setNamSinh(etNamSinh.getText().toString());
                           if(validate() > 0){
                               if(thanhVienDao.updata(thanhVien) > 0){
                                   Toast.makeText(context, "Cập Nhập Thành Công", Toast.LENGTH_SHORT).show();

                               }else {
                                   Toast.makeText(context, "Cập Nhập Thất Bại", Toast.LENGTH_SHORT).show();
                               }
                           }
                               thanhVienFragment.setData();
                               dialog.dismiss();
                           }

                   });
                   dialog.show();

               }
           });
       }else {
           holder.tvMaTT.setText("null");
           holder.tvTenTT.setText("null");
           holder.tvNSTT.setText("null");
       }


    }

    public int validate(){
        if(etTenTT.getText().toString().isEmpty() || etNamSinh.getText().toString().isEmpty()){
            Toast.makeText(context, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvMaTT,tvTenTT,tvNSTT;
        ImageView btnXoa,btnSua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaTT = itemView.findViewById(R.id.tvMaTT);
            tvTenTT = itemView.findViewById(R.id.tvTenTT);
            tvNSTT = itemView.findViewById(R.id.tvNamSinh);
            btnXoa = itemView.findViewById(R.id.iVXoaThanhVien);
            btnSua = itemView.findViewById(R.id.iVUpdate);
        }
    }
}
