package com.mobilepolice.office.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyLazyFragment;
import com.mobilepolice.office.base.OpenFileUtil;
import com.mobilepolice.office.bean.ContactsData;
import com.mobilepolice.office.bean.EmailBean;
import com.mobilepolice.office.ui.adapter.EmailAdapter;
import com.mobilepolice.office.widget.StackLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目界面跳转示例
 */
public class MainFragmentD_Final extends MyLazyFragment
        implements View.OnClickListener {

    @BindView(R.id.viewStack)
    StackLayout viewStack;
    //
    Page_0_ViewHolder p0;
    Page_1_ViewHolder p1;
    Page_2_Sender_ViewHolder p2_sender;
    Page_2_Details_ViewHolder p2_details;
    View p1_View;
    View p2_ViewSender;
    View p2_ViewDetails;

    String path;

    public static MainFragmentD_Final newInstance() {
        return new MainFragmentD_Final();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.email_root;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
    }

    ArrayList<EmailBean> data = new ArrayList<>();

    int code = 1101;

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, code);
        }
        View view = LayoutInflater.from(getContext()).inflate(R.layout.email_root_p0, viewStack, false);
        p1_View = LayoutInflater.from(getContext()).inflate(R.layout.email_content_p1, viewStack, false);
        p2_ViewSender = LayoutInflater.from(getContext()).inflate(R.layout.email_sender_p2, viewStack, false);
        p2_ViewDetails = LayoutInflater.from(getContext()).inflate(R.layout.email_receiver_p2, viewStack, false);
        p2_sender = new Page_2_Sender_ViewHolder(p2_ViewSender);
        p2_details = new Page_2_Details_ViewHolder(p2_ViewDetails);
        p1 = new Page_1_ViewHolder(p1_View);
        p0 = new Page_0_ViewHolder(view);
        viewStack.push(view);
        data.add(new EmailBean("刘爽", "吉林省厅新一代项目--软件验收需要整理的资料及时间【重要】", "2019/04/06 12:00" ,"@杜工  段工：\n" +
                "\n" +
                "  今天和监理确实了一些我们信大整理一份文档，见附件。\n" +
                "\n" +
                "目前按照按照监理反馈，就使用我们的模板就可以。\n" +
                "\n" +
                "需要下面3个文档（不要需求设计的文档了）\n" +
                "29 用户使用手册\n" +
                "30 操作手册（运维使用的）\n" +
                "38 测试报告\n" +
                "\n" +
                "注意事项：\n" +
                "1.每个厂商的使用手册整理成一个，同样操作手册和测试报告也整理成一个。最终汇总到信大，信大再把三个厂商汇总成一个文档。\n" +
                "2.终端app不需要提供单独操作手册，只需要提供app后台的操作手册。\n" +
                "3.模板一定要使用我们给的，否则整合到一块不好汇总\n" +
                "\n" +
                "时间要求：2019.5.24中午前给hmy@xdja.com，信大在汇总\n" +
                "\n" +
                "\n" +
                "@杜工  段工：\n" +
                "     上次提供额成品软件报验材料里面涉及盖章和项目经理栏位，都需要填写信大的项目经理徐凯军及盖信大的章，麻烦确认一下是否同意，同意请回复，谢谢！"));
        data.add(new EmailBean("甜心玥", "常住人员基本信息，机动车基本信息", "2019/04/03 12:00","同意，周五之前给提供附件内容"));
        data.add(new EmailBean("雨田", "常住人员基本信息，机动车基本信息", "2019/04/01 12:00","@杜工  段工：\n" +
                "\n" +
                " \n" +
                "\n" +
                "软件授权书由于监理反馈不需要你们填写证书依据，主要是合同不是你们直接签订的。所以我把你们的授权书上面的证书依据删除了，直接使用附件的就可以。\n" +
                "\n" +
                " \n" +
                "\n" +
                "  目前监理反馈需要以下资料：\n" +
                "\n" +
                "   1.盖章的软件授权书（盖章）       3份\n" +
                "\n" +
                "   2.软件著作权证书（盖章）            3份\n" +
                "\n" +
                " \n" +
                "\n" +
                "谢谢"));
        data.add(new EmailBean("vesslan", "项目申报书第三版", "2019/04/04 12:00",""));
        data.add(new EmailBean("20222773", "上架应用流程", "2019/03/28 12:00",""));
        data.add(new EmailBean("雨田", "吉林公安企业微信工作安排", "2019/03/26 12:00",""));


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                InputStream open = getContext().getAssets().open("files.docx");
                int len;
                byte[] buff = new byte[512];
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                File root = new File(externalStorageDirectory, "extras");
                root.mkdir();
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(root, "files.docx")));
                while ((len = open.read(buff)) > 0)
                    out.write(buff, 0, len);
                open.close();
                out.flush();
                out.close();
                emitter.onNext(new File(root, "files.docx").getAbsolutePath());
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(this::copyFinish, this::err, this::complete)
                .isDisposed();
    }

    private void complete() {

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void copyFinish(String path) {
        this.path = path;
        Log.e("s", "copyFinish: "+path );
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == code && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            toast("sd卡读写权限不足");
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        onViewCreated2(view, savedInstanceState);
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initData() {

    }


    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
    }


    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        //return !super.isStatusBarEnabled();
        return false;
    }

    @Override
    public boolean statusBarDarkFont() {
        return false;
    }

    private HashMap<String, ArrayList<ContactsData>> userInfoCache = new HashMap<>();


    class Page_0_ViewHolder {


        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.tv_mailbox_main_add)
        TextView tvMailboxMainAdd;
        @BindView(R.id.class_id_01)
        LinearLayout classId01;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.ll_collect)
        LinearLayout llCollect;
        @BindView(R.id.ll_send)
        LinearLayout llSend;
        @BindView(R.id.ll_drafts)
        LinearLayout llDrafts;
        @BindView(R.id.ll_mailbox_main)
        LinearLayout llMailboxMain;

        Page_0_ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.ll_collect)
        void onCollectPressed() {
            viewStack.push(p1_View);


            p1.set("收件箱", data.subList(0, 3));
        }

        @OnClick(R.id.tv_mailbox_main_add)
        void addNew() {
            viewStack.push(p2_ViewSender);
        }

        @OnClick(R.id.ll_send)
        void onSendPressed() {
            viewStack.push(p1_View);
            p1.set("发件箱", data.subList(3, data.size()));
        }

        @OnClick(R.id.ll_drafts)
        void ll_drafts() {
            viewStack.push(p1_View);
            p1.set("草稿箱", new ArrayList<>());
        }
    }


    class Page_1_ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.rv_recevice_mRecyclerView)
        RecyclerView rvReceviceMRecyclerView;
        @BindView(R.id.ll_mailbox_recevice_main)
        LinearLayout llMailboxReceviceMain;
        @BindView(R.id.indicator)
        TextView indicator;

        EmailAdapter adapter;

        Page_1_ViewHolder(View view) {
            adapter = new EmailAdapter();
            ButterKnife.bind(this, view);
            rvReceviceMRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            rvReceviceMRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener((_a, view1, position) -> {
                viewStack.push(p2_ViewDetails);
                p2_details.setDetails(adapter.getItem(position));
            });
        }

        @OnClick(R.id.title)
        void back() {
            viewStack.pop();
        }

        void set(String title, List<EmailBean> bean) {
            this.title.setText(title);
            adapter.setNewData(bean);
            if (adapter.getItemCount() > 0) {
                rvReceviceMRecyclerView.setVisibility(View.VISIBLE);
                indicator.setVisibility(View.GONE);
            } else {
                rvReceviceMRecyclerView.setVisibility(View.GONE);
                indicator.setVisibility(View.VISIBLE);

            }
        }
    }

    class Page_2_Sender_ViewHolder {
        @BindView(R.id.tv_mailbox_create_title)
        TextView tvMailboxCreateTitle;
        @BindView(R.id.tv_mailbox_send)
        TextView tvMailboxSend;
        @BindView(R.id.et_mailbox_consignee)
        EditText etMailboxConsignee;
        @BindView(R.id.img_contacts)
        ImageView imgContacts;
        @BindView(R.id.et_mailbox_title)
        EditText etMailboxTitle;
        @BindView(R.id.et_mailbox_content)
        EditText etMailboxContent;
        @BindView(R.id.saveEmail)
        TextView saveEmail;
        @BindView(R.id.cancleEmail)
        TextView cancleEmail;
        @BindView(R.id.mask)
        LinearLayout mask;
        @BindView(R.id.ll_mailbox_send)
        LinearLayout llMailboxSend;

        Page_2_Sender_ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.tv_mailbox_create_title)
        void onBack() {
            viewStack.pop();
        }
    }


    class Page_2_Details_ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.receiver)
        TextView receiver;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.fileName)
        TextView fileName;
        @BindView(R.id.mBack)
        ImageView back;
        @BindView(R.id.fileDetails)
        LinearLayout fileDetails;

        @OnClick(R.id.mBack)
        void onBack() {
            viewStack.pop();
        }

        @OnClick(R.id.fileDetails)
        void fileDetails() {
            if (!TextUtils.isEmpty(path)) {
                Intent intent = OpenFileUtil.openFile(path);
                try {
                    getContext().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "不支持的文件类型，无法打开文件", Toast.LENGTH_SHORT).show();
                }

            }

        }

        Page_2_Details_ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


        void setDetails(EmailBean bean) {
            title.setText(bean.getTitle());
            name.setText("王秘书    " + bean.getDate());
            receiver.setText("收件人：" + bean.getName());
            content.setText(bean.getContent());
        }
    }
}