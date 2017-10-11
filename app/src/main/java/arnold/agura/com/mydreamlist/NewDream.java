package arnold.agura.com.mydreamlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import static arnold.agura.com.mydreamlist.R.id.imageView;


public class NewDream extends AppCompatActivity {
    ImageView mThumbnail;
    TextView mName,mDescription,mPrice;
    Button mAdd;
    Dream dream;
    int count;
    Bitmap bm;
    public static final String PASS_STRING = "";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_album);
        Intent intent = getIntent();

        mThumbnail = (ImageView)findViewById(imageView);
        mName = (TextView)findViewById(R.id.name);
        mDescription =(TextView) findViewById(R.id.description);
        mPrice = (TextView) findViewById(R.id.price);
        mAdd = (Button) findViewById(R.id.add);


        mThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendback();

            }
        });
    }
    public void sendback(){
        Bitmap bm=((BitmapDrawable)mThumbnail.getDrawable()).getBitmap();
        dream = new Dream(mName.getText().toString(),mDescription.getText().toString(),Float.parseFloat(mPrice.getText().toString()),getBytesFromBitmap(bm));
        //dream.setName(mName.getText().toString());
        // dream.setNumOfSongs(Integer.parseInt(mPrice.getText().toString()));
        // dream.setThumbnail(getBytesFromBitmap(mThumbnail.getDrawingCache()));
        Intent albumIntent = getIntent();
        albumIntent.putExtra(PASS_STRING, dream);
        setResult(Activity.RESULT_OK, albumIntent);
        finish();

    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mThumbnail.setImageBitmap(imageBitmap);
        }
    }
}
