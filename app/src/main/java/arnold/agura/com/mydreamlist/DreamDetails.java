package arnold.agura.com.mydreamlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import static android.R.attr.data;
import static android.R.drawable.ic_menu_delete;
import static arnold.agura.com.mydreamlist.R.drawable.dream;
import static java.security.AccessController.getContext;

public class DreamDetails extends AppCompatActivity {
    public static final String PASS_STRING = "qwe";
    ImageView mThumbnail,mDelete,mEdit,mCheck,mCancel;
    EditText mName,mDescription,mPrice;
    TextView txtName, txtDesc, txtPrice;
    Dream dream,edream;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_details);
          dream = (Dream)getIntent().getSerializableExtra(PASS_STRING);
        mThumbnail = (ImageView)findViewById(R.id.imageView);
        mName = (EditText) findViewById(R.id.name);
        mDescription = (EditText) findViewById(R.id.description);
        mPrice = (EditText) findViewById(R.id.price);
        txtName = (TextView) findViewById(R.id.txtName);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        mDelete = (ImageView) findViewById(R.id.imgDelete);
        mEdit = (ImageView) findViewById(R.id.imgEdit);
        mCheck = (ImageView) findViewById(R.id.imgCheck);
        mCancel = (ImageView) findViewById(R.id.imgCancel);
        Bitmap bmp = BitmapFactory.decodeByteArray(dream.getThumbnail(), 0, dream.getThumbnail().length);

        mThumbnail.setImageBitmap(bmp);
        txtName.setText(dream.getName());
        txtDesc.setText(dream.getDescription());
        txtPrice.setText(String.valueOf(dream.getPrice()));
        resetVisibility();
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog diaBox = AskDeleteOption();
                diaBox.show();
            }
        });

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtName.setVisibility(View.GONE);
                txtDesc.setVisibility(View.GONE);
                txtPrice.setVisibility(View.GONE);
                mEdit.setVisibility(View.GONE);
                mDelete.setVisibility(View.GONE);
                mName.setVisibility(View.VISIBLE);
                mDescription.setVisibility(View.VISIBLE);
                mPrice.setVisibility(View.VISIBLE);
                mCheck.setVisibility(View.VISIBLE);
                mCancel.setVisibility(View.VISIBLE);
            }
        });

        mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog diaBox = AskEditOption();
                diaBox.show();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetVisibility();
            }
        });
    }
    private void resetVisibility(){
        mName.setVisibility(View.GONE);
        mDescription.setVisibility(View.GONE);
        mPrice.setVisibility(View.GONE);
        mCheck.setVisibility(View.GONE);
        mCancel.setVisibility(View.GONE);
        txtName.setVisibility(View.VISIBLE);
        txtDesc.setVisibility(View.VISIBLE);
        txtPrice.setVisibility(View.VISIBLE);
        mEdit.setVisibility(View.VISIBLE);
        mDelete.setVisibility(View.VISIBLE);
    }
    private AlertDialog AskDeleteOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to DELETE this Dream?")


                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        passDeleteInfo();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }
    private AlertDialog AskEditOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("EDIT")
                .setMessage("Do you want to EDIT this Dream?")


                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        editDream();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }
    private void editDream(){
        edream = new Dream();
        edream.setName(mName.getText().toString());
        edream.setDescription(mDescription.getText().toString());
        edream.setPrice(Float.parseFloat(mPrice.getText().toString()));

        intent = new Intent(this, MainActivity.class);
        intent.putExtra("EDIT",edream);
        startActivity(intent);
    }
    private void passDeleteInfo(){
        String dreamName = dream.getName();
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("DELETE",dreamName);
        startActivity(intent);
    }
}

