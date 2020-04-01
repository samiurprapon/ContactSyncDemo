package me.prapon.contactsyncdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import me.prapon.contactsyncdemo.R;
import me.prapon.contactsyncdemo.model.Contact;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private Context mContext;
    private final LayoutInflater layoutInflater;
    private List<Contact> mContacts;

    public ContactAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.contact_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if(mContacts != null) {
            Contact contact = mContacts.get(position);
            viewHolder.mPhone.setText(contact.getPhoneNumber());
            viewHolder.mName.setText(contact.getName());

            viewHolder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void setContacts(List<Contact> contacts) {
        mContacts = contacts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mContacts != null) {
            return mContacts.size();
        } else {
            return 0;
        }
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mPhone;
        private MaterialCardView card;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.tv_name);
            mPhone = itemView.findViewById(R.id.tv_phone);
            card = itemView.findViewById(R.id.row_contact);
        }
    }
}
