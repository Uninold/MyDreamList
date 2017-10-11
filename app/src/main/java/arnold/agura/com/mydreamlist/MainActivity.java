package arnold.agura.com.mydreamlist;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.R.attr.id;
import static arnold.agura.com.mydreamlist.R.drawable.cover;
import static arnold.agura.com.mydreamlist.R.drawable.dreamcover;
import static arnold.agura.com.mydreamlist.R.id.imageView;


public class MainActivity extends Activity {
    TextView mName;
    FloatingActionButton mAddDream;
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Dream> dreamList;
    public Dream dream;
    Dream editDream;
    String dreamName;
    public static final String PASS_STRING = "qwe";
    public static final int REQ_CODE = 1111;
    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dreamName = getIntent().getStringExtra("DELETE");
        editDream = (Dream) getIntent().getSerializableExtra("EDIT");
        if(dreamName!=null)
            db.deleteDream(dreamName);
        if(editDream!=null)
            db.updateDream(editDream);
        display();

        try {
            Glide.with(this).load(dreamcover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mAddDream = (FloatingActionButton) findViewById(R.id.addDream);
        mAddDream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewDream();
            }
        });
    }
    private void display() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        dreamList = db.getAllAlbum();
        adapter = new AlbumsAdapter(this, dreamList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void addNewDream(){
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.photo);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();
        //dream = new Dream("name",0, bitMapData);
        dream = new Dream();
        Intent newAlbum = new Intent(this,NewDream.class);
        newAlbum.putExtra(PASS_STRING,dream);
        //newAlbum.putExtra(PASS_STRING, dream);
        startActivityForResult(newAlbum, REQ_CODE);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_CODE) {
                Dream dream = (Dream) data.getSerializableExtra(NewDream.PASS_STRING);
                db.addAlbum(dream);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
    }
}