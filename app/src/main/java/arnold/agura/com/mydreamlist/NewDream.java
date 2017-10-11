package arnold.agura.com.mydreamlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

import static arnold.agura.com.mydreamlist.R.id.imageView;


public class NewDream extends AppCompatActivity {
    ImageView mThumbnail;
    TextView mName,mDescription,mPrice;
    Button mAdd;
    TextInputLayout mInputName, mInputDescription, mInputPrice;
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
        mInputName = (TextInputLayout) findViewById(R.id.input_layout_name);
        mInputDescription = (TextInputLayout) findViewById(R.id.input_layout_description);
        mInputPrice = (TextInputLayout) findViewById(R.id.input_layout_price);

        mName.addTextChangedListener(new MyTextWatcher(mName));
        mDescription.addTextChangedListener(new MyTextWatcher(mDescription));
        mPrice.addTextChangedListener(new MyTextWatcher(mPrice));

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

    private boolean validateName() {
        if (mName.getText().toString().trim().isEmpty()) {
            mInputName.setError("Enter name here");
            requestFocus(mName);
            return false;
        } else {
            mInputName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDescription() {
        if (mDescription.getText().toString().trim().isEmpty()) {
            mInputDescription.setError("Enter name here");
            requestFocus(mDescription);
            return false;
        } else {
            mInputDescription.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePrice() {
        if (mPrice.getText().toString().trim().isEmpty()) {
            mInputPrice.setError("Enter name here");
            requestFocus(mPrice);
            return false;
        } else {
            mInputPrice.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_layout_name:
                    validateName();
                    break;
                case R.id.input_layout_description:
                    validateDescription();
                    break;
                case R.id.input_layout_price:
                    validatePrice();
                    break;
            }
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
