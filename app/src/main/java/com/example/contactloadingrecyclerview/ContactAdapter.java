package com.example.contactloadingrecyclerview;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter  extends RecyclerView.Adapter<ContactAdapter.ConatactHolder> {

    Context context;
    List<ContactModel> contactModelList=new ArrayList<>();

    AlertDialog alertDialog;

    public ContactAdapter(Context context, List<ContactModel> contactModelList) {
        this.context = context;
        this.contactModelList = contactModelList;
    }


    @NonNull
    @Override
    public ConatactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_contacts,parent,false);
        return new ConatactHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ConatactHolder holder, int position) {
        ContactModel contactModel=contactModelList.get(position);

        holder.name.setText(contactModelList.get(position).getName());
        holder.number.setText(contactModelList.get(position).getNumber());
        holder.imageView.setImageURI(contactModelList.get(position).imageuri);

        if (contactModelList.get(position).getImage()!=null){
            holder.imageView.setImageURI(Uri.parse(""+contactModelList.get(position).getImage()));
        }
        else {
            Log.i("null",""+contactModelList.get(position).getImage());
        }

    }


    @Override
    public int getItemCount() {
        return contactModelList.size();
    }

    public class ConatactHolder extends RecyclerView.ViewHolder {
        TextView name,number;
        ShapeableImageView imageView;
        public ConatactHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.contact_name);
            number=itemView.findViewById(R.id.contact_number);
            imageView=itemView.findViewById(R.id.contact_image);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        View view1 = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
                       ShapeableImageView displayimage=view1.findViewById(R.id.contactimage1);
                        TextView dialog=view1.findViewById(R.id.txt1);
                        dialog.setText(name.getText().toString());
                        Button cancel = view1.findViewById(R.id.bt1);

                        displayimage.setImageURI(Uri.parse(""+contactModelList.get(getAdapterPosition()).getImage()));


                      /*  if (contactModelList.get(getAdapterPosition()).getImage()!=null)
                        {
                            displayimage.setImageURI(Uri.parse(""+contactModelList.get(getAdapterPosition()).getImage()));

                        }else
                        {
                            displayimage.setImageResource(R.mipmap.ic_launcher);
                        }
*/

                        builder.setView(view1);
                        builder.setCancelable(false);

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(ContactAdapter.this.context, "cancel", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog=builder.create();
                        alertDialog.show();

                    }
                });
            }
        });

        }
    }

    /*public View getView(int i,View view,ViewGroup viewGroup){

        View root=layoutInflater.inflate(R.layout.custom_contacts,null);
        ShapeableImageView shapeableImageView=root.findViewById(R.id.contact_image);
        TextView name=root.findViewById(R.id.contact_name);
        TextView number=root.findViewById(R.id.contact_number);

        name.setText(contactModelList.get(i).getName());
        number.setText(contactModelList.get(i).getNumber());

        if(contactModelList.get(i).getImage()!=null)
        {
            shapeableImageView.setImageURI(Uri.parse(""+contactModelList.get(i).getImage()));

        }
        else

        {
            Log.i("null",""+contactModelList.get(i).getImage());

        }
        return root;*/
    }

