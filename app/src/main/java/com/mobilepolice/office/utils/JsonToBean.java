package com.mobilepolice.office.utils;

import android.content.Context;
import android.text.TextUtils;

import com.mobilepolice.office.bean.NewsListBean;
import com.mobilepolice.office.bean.NoticeListBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JsonToBean {
    public static List<NewsListBean> getNewsList(Context context) {
        StringBuilder newstringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open("news.json");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                newstringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = newstringBuilder.toString();
        List<NewsListBean> list=null;
        if (!TextUtils.isEmpty(result)) {
            try {
                list = FastJsonHelper.deserializeList(result, NewsListBean.class);
            } catch (Exception ex) {

            }
        } else {
            return null;
        }
        return list;
    }
    public static List<NoticeListBean> getNoticeList(Context context) {
        StringBuilder newstringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open("notice.json");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                newstringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = newstringBuilder.toString();
        List<NoticeListBean> list=null;
        if (!TextUtils.isEmpty(result)) {
            try {
                list = FastJsonHelper.deserializeList(result, NoticeListBean.class);
            } catch (Exception ex) {

            }
        } else {
            return null;
        }
        return list;
    }
}
