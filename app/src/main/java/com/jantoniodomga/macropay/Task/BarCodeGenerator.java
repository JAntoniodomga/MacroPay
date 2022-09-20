package com.jantoniodomga.macropay.Task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.jantoniodomga.macropay.Contracts.IResultable;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarCodeGenerator extends Thread{
    private String content;
    private IResultable<Bitmap> listener;

    public BarCodeGenerator(String content, IResultable<Bitmap> listener) {
        this.content = content;
        this.listener = listener;
    }

    @Override
    public void run() {
        super.run();
        try {
            BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
            Bitmap bitmap=barcodeEncoder.encodeBitmap(content, BarcodeFormat.PDF_417,1200,400);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();

            paint.setColor(Color.BLACK);
            paint.setTextSize(25);
            paint.setAntiAlias(true);
            canvas.drawText(content,350,300,paint);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    listener.onSuccess(bitmap);
                }
            });
        } catch (WriterException e) {
            e.printStackTrace();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    listener.onFailure(e);
                }
            });

        }
    }
}
