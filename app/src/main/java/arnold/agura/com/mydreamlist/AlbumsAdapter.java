package arnold.agura.com.mydreamlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import static arnold.agura.com.mydreamlist.MainActivity.PASS_STRING;
import static arnold.agura.com.mydreamlist.MainActivity.REQ_CODE;
import static arnold.agura.com.mydreamlist.R.drawable.dream;

/**
 * Created by Arnold on 8 Oct 2017.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Dream> dreamList;
    public static final String PASS_STRING = "qwe";
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

        }
    }


    public AlbumsAdapter(Context mContext, List<Dream> dreamList) {
        this.mContext = mContext;
        this.dreamList = dreamList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Dream dream = dreamList.get(position);
        holder.name.setText(dream.getName());
        holder.price.setText("P "+dream.getPrice() );

        // loading dream cover using Glide library
        Glide.with(mContext).load(dream.getThumbnail()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Dream passDream = dreamList.get(position);
                Intent intent = new Intent(view.getContext(), DreamDetails.class);
                intent.putExtra(PASS_STRING,passDream);
                view.getContext().startActivity(intent);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */


    /**
     * Click listener for popup menu items
     */


    @Override
    public int getItemCount() {
        return dreamList.size();
    }
}