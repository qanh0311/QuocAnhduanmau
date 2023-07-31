package com.example.asm.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.R;
import com.example.asm.SQLite.Dao.PhieuMuonDao;
import com.example.asm.SQLite.Dao.SachDao;
import com.example.asm.SQLite.Dao.ThanhVienDao;
import com.example.asm.SQLite.model.PhieuMuon;
import com.example.asm.SQLite.model.Sach;
import com.example.asm.SQLite.model.ThanhVien;
import com.example.asm.ui.home.phieuMuon.phieuMuonFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Reciview_Adapter_PhieuMuon  extends RecyclerView.Adapter<Reciview_Adapter_PhieuMuon.viewHolder>{
    Context context;
    List<PhieuMuon> list;
    phieuMuonFragment phieuMuonFragment;
    ThanhVienDao thanhVienDao;
    SachDao sachDao;
    PhieuMuonDao phieuMuonDao;
    PhieuMuon phieuMuon;

    Dialog dialog;
    EditText etMaPM ;
    TextView  etNgayMuon, etTienThue;
    Button btnSave,btnCacel;
    Spinner spinnerS, spinnerTV;
    CheckBox checkBoxPM;
    int maS,maTV,viTriS,viTriTV,tienthue;
    List<Sach> listS;
    List<ThanhVien> listTV;

    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    public Reciview_Adapter_PhieuMuon(Context context, List<PhieuMuon> list, phieuMuonFragment phieuMuonFragment) {
        this.context = context;
        this.list = list;
        this.phieuMuonFragment = phieuMuonFragment;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_pm,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        thanhVienDao = new ThanhVienDao(context);
        sachDao = new SachDao(context);
        phieuMuonDao = new PhieuMuonDao(context);
        phieuMuon = new PhieuMuon();
        if(list != null ){
            holder.tvMaPM.setText("Mã phiếu mượn : "+list.get(position).getMaPM());
            holder.tvTenTT.setText("Tên thành viên : "+thanhVienDao.getId(String.valueOf(list.get(position).getMaTV())).getHoTenTV());
            holder.tvTenSach.setText("Tên sách : "+sachDao.getId(String.valueOf(list.get(position).getMaSach())).getTenSach());
            holder.tvTienThue.setText("Tiên thuê : "+list.get(position).getTienThue());
            if(list.get(position).getTrangThai() == 1){
                holder.tvTrangThai.setText("Chưa Trả");
            }else {
                holder.tvTrangThai.setText("Đã Trả");
            }
            holder.tvNgayMuon.setText(format.format(list.get(position).getNgayMuon()));

            holder.btnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xóa")
                            .setMessage("Bạn muốn xóa")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(phieuMuonDao.delete(String.valueOf(list.get(holder.getAdapterPosition()).getMaPM())) > 0){
                                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                        phieuMuonFragment.setData();
                                    }
                                    else {
                                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
            holder.btnSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.item_dialog_pm);
                    etMaPM = dialog.findViewById(R.id.etMaPMDialog);
                    etNgayMuon =dialog.findViewById(R.id.etNgayMuonDialog);
                    etTienThue = dialog.findViewById(R.id.etTienThueDialog);
                    spinnerS = dialog.findViewById(R.id.spinnerLSPM);
                    spinnerTV = dialog.findViewById(R.id.spinnerTVPM);
                    btnSave = dialog.findViewById(R.id.btnSavePM);
                    btnCacel = dialog.findViewById(R.id.btnCancelPM);
                    checkBoxPM = dialog.findViewById(R.id.checkBoxPM);

                    // spinner
                    listS = sachDao.getAll();
                    Spinner_Adapter_Sach adapterSach = new Spinner_Adapter_Sach(context,listS);
                    spinnerS.setAdapter(adapterSach);
                    spinnerS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            maS = listS.get(i).getMaSach();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    listTV = thanhVienDao.getAll();
                    Spinner_Adapter_TV adapterTV = new Spinner_Adapter_TV(context,listTV);
                    spinnerTV.setAdapter(adapterTV);
                    spinnerTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            maTV = listTV.get(i).getMaTV();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    // do du lieu len
                    etMaPM.setEnabled(false);
                    etMaPM.setText(String.valueOf(list.get(holder.getAdapterPosition()).getMaPM()));
                    etNgayMuon.setText(format.format(list.get(holder.getAdapterPosition()).getNgayMuon()));
                    etTienThue.setText(String.valueOf(list.get(holder.getAdapterPosition()).getTienThue()));
                    if(list.get(holder.getAdapterPosition()).getTrangThai() == 0){
                        checkBoxPM.setChecked(true);
                    }else {
                        checkBoxPM.setChecked(false);
                    }

                    for(int i = 0 ; i < listS.size() ; i ++){
                        if(list.get(holder.getAdapterPosition()).getMaSach() == listS.get(i).getMaSach()){
                            viTriS = i;
                        }
                    }
                    spinnerS.setSelection(viTriS);
                    for(int i = 0 ; i < listTV.size() ; i ++){
                        if(list.get(holder.getAdapterPosition()).getMaTV() == listTV.get(i).getMaTV()){
                            viTriTV = i;
                        }
                    }
                    spinnerTV.setSelection(viTriTV);
                    //



                    // xu li
                   btnSave.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {

                           if(validate() > 0){

                               // ma
                               phieuMuon.setMaPM(Integer.parseInt(etMaPM.getText().toString()));
                               phieuMuon.setMaSach(maS);
                               phieuMuon.setMaTV(maTV);
                               // ngay
                               try {
                                   phieuMuon.setNgayMuon(format.parse(etNgayMuon.getText().toString()));
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               // tien
                               phieuMuon.setTienThue(Integer.parseInt(etTienThue.getText().toString()));
                               // trang thai
                               if(checkBoxPM.isChecked()){
                                   phieuMuon.setTrangThai(0);
                               }else {
                                   phieuMuon.setTrangThai(1);
                               }

                               if(phieuMuonDao.updata(phieuMuon) > 0){
                                   Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                               }else {
                                   Toast.makeText(context, "cập nhật thất bại", Toast.LENGTH_SHORT).show();
                               }
                               phieuMuonFragment.setData();
                               dialog.dismiss();
                           }
                       }
                   });
                   btnCacel.setOnClickListener(new View.OnClickListener() {
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
        if(etNgayMuon.getText().toString().isEmpty() || etTienThue.getText().toString().isEmpty()){
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
        TextView tvMaPM,tvTenTT,tvTenSach,tvTienThue,tvTrangThai,tvNgayMuon;
        ImageView btnXoa,btnSua;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPM  = itemView.findViewById(R.id.tvMaPM);
            tvTenTT  = itemView.findViewById(R.id.tvTenTTPM);
            tvTenSach = itemView.findViewById(R.id.tvTenSachPM);
            tvTienThue = itemView.findViewById(R.id.tvTienThuePM);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            tvNgayMuon = itemView.findViewById(R.id.tvNgayMuon);

            btnXoa = itemView.findViewById(R.id.iVXoaPM);
            btnSua = itemView.findViewById(R.id.iVUpdatePM);
        }
    }
}
