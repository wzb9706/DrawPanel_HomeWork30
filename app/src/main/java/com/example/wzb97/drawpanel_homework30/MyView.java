package com.example.wzb97.drawpanel_homework30;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    final int RECT_TYPE=1;
    final int CIRCLE_TYPE=2;
    String path;
    Bitmap im;
    Boolean isSet=false;
    Boolean isMaSet=false;
    MainActivity ma;
    int type=1;
    int length=40,side;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMa(MainActivity ma){
        isMaSet=true;
        this.ma=ma;
    }

    public void setPath(String s){
        length=40;
        side=2;
        path=s;
        im = BitmapFactory.decodeFile(path);
        //test
        int height=im.getHeight();
        int width=im.getWidth();
        int h=getHeight();
        int w=getWidth();
        isSet=true;
        System.out.println(height+","+width+","+h+","+w);
        invalidate();
    }
    public void setType(int type){
        this.type=type;
        if(isSet)invalidate();
    }

    public void setLength(int length){
        if(this.length>(side)*2+1){
            this.length=length;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        if(isSet){
            im = BitmapFactory.decodeFile(path);
            double startx,endx,starty,endy;
            double bh=im.getHeight();
            double bw=im.getWidth();
            double w=getWidth();
            double h=getHeight();
            side=2;
            double temph,tempw;
            temph=bh;
            tempw=bw;
            if(bw<=w&&bh<=h){
                startx=(w-bw)/2;
                endx=startx+bw;
                starty=(h-bh)/2;
                endy=starty+bh;
            }
            else{
                if(bw/w>bh/h){
                    temph=(h*(w/bw));
                    tempw=w;
                    startx=0;
                    endx=w;
                    starty=(h-temph)/2;
                    endy=starty+temph;
                }
                else{
                    tempw=(w*(h/bh));
                    temph=h;
                    startx=(w-tempw)/2;
                    endx=startx+tempw;
                    starty=0;
                    endy=h;
                }
            }
            startx=(int)startx;
            starty=(int)starty;
//            System.out.println("tempx,tempy:"+tempw+","+temph);
//            System.out.println(startx+","+endx+" "+starty+","+endy);
            for(int i=(int)startx;i<endx-length;i+=length){
                for(int j=(int)starty;j<endy-length;j+=length){
                    int tempi=(int)(bw*((i-startx)/tempw));
                    int tempj=(int)(bh*((j-starty)/temph));
                    int  red   = (im.getPixel(tempi,tempj) & 0x00ff0000) >> 16;  //取高两位
                    int  green = (im.getPixel(tempi,tempj) & 0x0000ff00) >> 8; //取中两位
                    int  blue  =  im.getPixel(tempi,tempj) & 0x000000ff;
                    paint.setColor(Color.rgb(red,green,blue));
//                    paint.setStyle(Paint.Style.STROKE);
//                    paint.setStrokeWidth((length)/2-side-side);
                    if(type==RECT_TYPE)canvas.drawRect(i+side,j+side,i+length-side,j+length-side,paint);
                    else canvas.drawCircle(i+side,j+side,length,paint);
                }
            }
        }

    }
    //    void drawStrokes() {
//        if (memCanvas == null) {
//
//            //缓冲位图
//            memBMP = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
//
//            //缓冲画布
//            memCanvas = new Canvas();
//
//            //为画布设置位图，图形实际保存在位图中
//            memCanvas.setBitmap(memBMP);
//
//            //画笔
//            memPaint = new Paint();
//
//            //抗锯齿
//            memPaint.setAntiAlias(true);
//
//            //画笔颜色
//            memPaint.setColor(Color.RED);
//
//            //设置填充类型
//            memPaint.setStyle(Paint.Style.STROKE);
//
//            //设置画笔宽度
//            memPaint.setStrokeWidth(5);
//
//        }
//
//        for (Path path : listStrokes) {
//            memCanvas.drawPath(path, memPaint);
//        }
//
//        //刷新屏幕
//        invalidate();
//
//    }
}