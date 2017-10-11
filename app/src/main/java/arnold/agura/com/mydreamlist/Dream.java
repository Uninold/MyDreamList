package arnold.agura.com.mydreamlist;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Arnold on 8 Oct 2017.
 */
public class Dream implements Serializable{
    private String name;
    private String description;
    private float price;
    private byte[] thumbnail;
    public Dream(String name, String description, float price, byte[] thumbnail) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.thumbnail = thumbnail;
    }



    public Dream() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }
}
