package com.m.qrgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {


    // variables for imageview, edittext,
    // button, bitmap and qrencoder.
    private ImageView qrCodeIV;
    private EditText dataEdt,dataEdt1,dataEdt2;
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing all variables.
        qrCodeIV = findViewById(R.id.idIVQrcode);
        dataEdt = findViewById(R.id.idEdt);
        dataEdt1 = findViewById(R.id.idEdt1);
        dataEdt2 = findViewById(R.id.idEdt2);
        generateQrBtn = findViewById(R.id.idBtnGenerateQR);

        // initializing onclick listener for button.
        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(dataEdt.getText().toString())) {

                    // if the edittext inputs are empty then execute
                    // this method showing a toast message.
                    Toast.makeText(MainActivity.this, "Enter Upi Id to generate QR Code", Toast.LENGTH_SHORT).show();
                } else {
                    // below line is for getting
                    // the windowmanager service.
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                    // initializing a variable for default display.
                    Display display = manager.getDefaultDisplay();

                    // creating a variable for point which
                    // is to be displayed in QR Code.
                    Point point = new Point();
                    display.getSize(point);

                    // getting width and
                    // height of a point
                    int width = point.x;
                    int height = point.y;

                    // generating dimension from width and height.
                    int dimen = width < height ? width : height;
                    dimen = dimen * 3 / 4;
                    String upi=dataEdt.getText().toString();
                    String name=dataEdt2.getText().toString().replace(" ","%20");
                    String amount=dataEdt1.getText().toString();

                    // setting this dimensions inside our qr code
                    // encoder to generate our qr code.
                    String data = "upi://pay?pa=" +upi+"&am="+amount+"&pn="+name+   // payment method.\n" +
                            //"ripal4910@okhdfcbank" +         // VPA number.\n" +
                            //"&am=99999.99 "+        // this param is for fixed amount (non editable).\n" +
                            //"&pn=RipalPatel"+      // to showing your name in app.\n" +
                            "&cu=INR" +                  // Currency code.\n" +
                            "&mode=02";                  // mode O2 for Secure QR Code.\n" +
                     //       "&orgid=189999" +            //If the transaction is initiated by any PSP app then the respective orgID needs to be passed.\n" +
                     //       "&sign=MEYCIQC8bLDdRbDhpsPAt9wR1a0pcEssDaV" +   // Base 64 encoded Digital signature needs to be passed in this tag\n" +
                      //      "Q7lugo8mfJhDk6wIhANZkbXOWWR2lhJOH2Qs/OQRaRFD2oBuPCGtrMaVFR23t";
                    qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, dimen);
                    try {
                        // getting our qrcode in the form of bitmap.
                        bitmap = qrgEncoder.encodeAsBitmap();
                        // the bitmap is set inside our image
                        // view using .setimagebitmap method.
                        qrCodeIV.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        // this method is called for
                        // exception handling.
                        Log.e("Tag", e.toString());
                    }
                }
            }
        });
    }
}