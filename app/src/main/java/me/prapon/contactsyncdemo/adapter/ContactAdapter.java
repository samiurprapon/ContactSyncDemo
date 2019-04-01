package me.prapon.contactsyncdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import me.prapon.contactsyncdemo.R;
import me.prapon.contactsyncdemo.model.Contact;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    ArrayList<Contact> contacts;
    Context mContext;

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        this.mContext = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Contact contact = contacts.get(position);

        viewHolder.mName.setText(contact.getName());
        viewHolder.mPhone.setText(contact.getPhone());


        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void updateData(ArrayList<Contact> newContacts) {
        contacts.clear();
        contacts.addAll(newContacts);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mPhone;
        MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tvName);
            mPhone = itemView.findViewById(R.id.tvPhone);
            card = itemView.findViewById(R.id.row_contact);
        }
    }
}
