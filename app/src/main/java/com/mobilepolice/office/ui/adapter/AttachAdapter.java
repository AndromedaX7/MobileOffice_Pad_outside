package com.mobilepolice.office.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.CollectEmailBean;
import com.mobilepolice.office.http.HttpConnectInterface;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.forward.androids.utils.ImageUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AttachAdapter extends BaseQuickAdapter<CollectEmailBean.ObjBean.AttachBean, BaseViewHolder> {
    public AttachAdapter() {
        super(R.layout.attach_item, null);
    }


    @Override
    protected void convert(BaseViewHolder helper, CollectEmailBean.ObjBean.AttachBean item) {
        if (item.getFileName() == null) {
//            mData.remove(item);
//            notifyDataSetChanged();
            return;
        }
        String fileName = item.getFileName();

        String filePath = item.getFilePath();
        Log.e("convert: ", filePath);
        Log.e("convert: ", item.getFileName());
        helper.setText(R.id.attach_name, fileName);
        ImageView img = helper.getView(R.id.attach_deco);

        String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        Log.e("convert: ", filePath);
        if (fileType.indexOf("doc") > -1) {
            img.setImageResource(R.drawable.file_doc);
        } else if (fileType.indexOf("xl") > -1) {
            img.setImageResource(R.drawable.xls);
        } else if (fileType.indexOf("pdf") > -1) {
            img.setImageResource(R.drawable.pdf);
        } else if (fileType.indexOf("ppt") > -1) {
            img.setImageResource(R.drawable.ppt);
        } else if (fileType.indexOf("zip") > -1 || fileType.indexOf("rar") > -1) {
            img.setImageResource(R.drawable.file_archive);
        } else if (fileType.indexOf("jp") > -1 || fileType.indexOf("png") > -1) {
            img.setImageResource(R.drawable.file_image);
        } else {
            img.setImageResource(R.drawable.file_doc);
        }
        Button downLoad = helper.getView(R.id.download);
        downLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpConnectInterface.getImage(filePath.replaceAll("10.106.12.104:8789","192.168.20.228:7121"))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::imageRes, this::error, this::onComplete)
                        .isDisposed();
            }

            private void onComplete() {

            }

            private void error(Throwable throwable) {
            }

            private void imageRes(byte[] bytes) {
                Context context = downLoad.getContext();
                File filesContainer = context.getExternalFilesDir(".file_container");
                OutputStream out = null;
                try {
                    if (!filesContainer.exists()) {
                        filesContainer.mkdirs();
                    }
                    File file = new File(filesContainer, "abc"+fileName.substring(fileName.indexOf("."),fileName.length()));
                    if (file.exists()) {
                        file.delete();
                    }
                    out = new FileOutputStream(file);
                    out.write(bytes);
                    out.flush();

///////////////////////////////////////////////////////////
//                    Intent intent = new Intent();
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.setAction(Intent.ACTION_VIEW);
//                    String type = getMIMEType(file);
//                    Log.e(TAG, "文件类型: "+type );
//                    //设置intent的data和Type属性。
//                    intent.setDataAndType(getUriForFile(context,file), type);
//                    context.startActivity(intent);

                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                    String type = getMIMEType(file);
                    intent.setDataAndType(getUriForFile(context,file), type);

//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(context, file));
                    context.startActivity(intent);
///////////////////////////////////////////////////////////
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            }
        });
    }

    public int downloadFile(View view, String fileName, String urlStr) {
        OutputStream output = null;
        File filesContainer = view.getContext().getExternalFilesDir("file_container");
        try {
            //将字符串形式的path,转换成一个url
            URL url = new URL(urlStr);
            //得到url之后，将要开始连接网络，以为是连接网络的具体代码
            //首先，实例化一个HTTP连接对象conn
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //定义请求方式为GET，其中GET的大小写不要搞错了。
            conn.setRequestMethod("GET");
            //定义请求时间，在ANDROID中最好是不好超过10秒。否则将被系统回收。
            conn.setConnectTimeout(6 * 1000);
            //请求成功之后，服务器会返回一个响应码。如果是GET方式请求，服务器返回的响应码是200，post请求服务器返回的响应码是206（貌似）。
            if (conn.getResponseCode() == 200) {
                //返回码为真
                //从服务器传递过来数据，是一个输入的动作。定义一个输入流，获取从服务器返回的数据
                InputStream input = conn.getInputStream();
                if (!filesContainer.exists()) {
                    filesContainer.mkdirs();
                }

                File file = new File(filesContainer, fileName);
                if (file.exists()) {
                    file.delete();
                }
                output = new FileOutputStream(file);
                Log.e("downloadFile: ", "" + output);
                //读取大文件
                byte[] buffer = new byte[1024];
                //记录读取内容
                int n = input.read(buffer);
                //写入文件
                output.write(buffer, 0, n);
                n = input.read(buffer);
                output.flush();
                input.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
                System.out.println("success");
                return 0;
            } catch (IOException e) {
                System.out.println("fail");
                e.printStackTrace();
            }
        }
        return 1;
    }
    private static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.mobilepolice.office.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
    public static String getMIMEType(File file) {
        String type = "*/*";
        String name = file.getName();
        int index = name.lastIndexOf('.');
        if (index < 0) {
            return type;
        }
        String end = name.substring(index, name.length()).toLowerCase();
        if (TextUtils.isEmpty(end)) return type;
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0])) type = MIME_MapTable[i][1];
        }
        return type;
    }

    private static final String[][] MIME_MapTable = {
            {".3gp", "video/3gpp"}, {".apk", "application/vnd.android.package-archive"}, {".asf", "video/x-ms-asf"}, {".avi", "video/x-msvideo"}, {".bin", "application/octet-stream"}, {".bmp", "image/bmp"}, {".c", "text/plain"}, {".class", "application/octet-stream"}, {".conf", "text/plain"}, {".cpp", "text/plain"}, {".doc", "application/msword"}, {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}, {".xls", "application/vnd.ms-excel"}, {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, {".exe", "application/octet-stream"}, {".gif", "image/gif"}, {".gtar", "application/x-gtar"}, {".gz", "application/x-gzip"}, {".h", "text/plain"}, {".htm", "text/html"}, {".html", "text/html"}, {".jar", "application/java-archive"}, {".java", "text/plain"}, {".jpeg", "image/jpeg"}, {".jpg", "image/jpeg"}, {".js", "application/x-javascript"}, {".log", "text/plain"}, {".m3u", "audio/x-mpegurl"}, {".m4a", "audio/mp4a-latm"}, {".m4b", "audio/mp4a-latm"}, {".m4p", "audio/mp4a-latm"}, {".m4u", "video/vnd.mpegurl"}, {".m4v", "video/x-m4v"}, {".mov", "video/quicktime"}, {".mp2", "audio/x-mpeg"}, {".mp3", "audio/x-mpeg"}, {".mp4", "video/mp4"}, {".mpc", "application/vnd.mpohun.certificate"}, {".mpe", "video/mpeg"}, {".mpeg", "video/mpeg"}, {".mpg", "video/mpeg"}, {".mpg4", "video/mp4"}, {".mpga", "audio/mpeg"}, {".msg", "application/vnd.ms-outlook"}, {".ogg", "audio/ogg"}, {".pdf", "application/pdf"}, {".png", "image/png"}, {".pps", "application/vnd.ms-powerpoint"}, {".ppt", "application/vnd.ms-powerpoint"}, {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"}, {".prop", "text/plain"}, {".rc", "text/plain"}, {".rmvb", "audio/x-pn-realaudio"}, {".rtf", "application/rtf"}, {".sh", "text/plain"}, {".tar", "application/x-tar"}, {".tgz", "application/x-compressed"}, {".txt", "text/plain"}, {".wav", "audio/x-wav"}, {".wma", "audio/x-ms-wma"}, {".wmv", "audio/x-ms-wmv"}, {".wps", "application/vnd.ms-works"}, {".xml", "text/plain"}, {".z", "application/x-compress"}, {".zip", "application/x-zip-compressed"}, {"", "*/*"}
    };


    /**
     *    * 在SD卡的指定目录上创建文件
     *    *
     *    * @param fileName
     *    
     */
    public File createFile(String fileName) {
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}