package com.mobilepolice.office.http;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class ResponseMe {

    private static OkHttpClient client;

    private static OkHttpClient client() {
        if(client == null){
            synchronized(Request.class){
                if(client == null){
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    client = builder.addNetworkInterceptor(interceptor)
                            .callTimeout(1,TimeUnit.MINUTES)
                            .connectTimeout(1,TimeUnit.MINUTES)
                            .readTimeout(1,TimeUnit.MINUTES)
                            .build();
                }
            }
        }
        return client;
    }

    /*静态方法，可以直接通过Response.codeUserInfo()来调用*/
     public static String saveEmail(String policeNumber,String title,String content,String receiver){
         MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
         RequestBody body = RequestBody.create(mediaType, "");// 添加请求参数
         Request request = new Request.Builder()
                 .url("http://192.168.20.228:7121/intranetmailws/mail/saveMailDraft?policeNumber="+policeNumber+"&title="+title+"&content="+content+"&receiver="+receiver)
//                 .get()
                 .post(body)
                 .addHeader("Content-Type","application/x-www-form-urlencoded")
                 .addHeader("Cache-Control", "no-cache")
                 .build();
         try{
             return client().newCall(request).execute().body().string();
         }catch (Exception e){
             e.printStackTrace();
             return null;
         }
     }
    public static String getDraftsEmail(String policeNumber){
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, ""); //添加请求参数
        Request request = new Request.Builder()
                .url("http://192.168.20.228:7121/intranetmailws/mail/findMailDraft?policeNumber="+policeNumber)
//                .get()
                .post(body)
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .addHeader("Cache-Control", "no-cache")
                .build();
        try{
            return client().newCall(request).execute().body().string();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static String deleteDraftEmal(String id){
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, ""); //添加请求参数
        Request request = new Request.Builder()
                .url("http://192.168.20.228:7121/intranetmailws/mail/deleteMailDraft?id="+id)
//                .get()
                .post(body)
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .addHeader("Cache-Control", "no-cache")
                .build();
        try{
            return client().newCall(request).execute().body().string();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
