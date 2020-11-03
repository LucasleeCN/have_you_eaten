package com.example.have_you_eaten.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.api.ApiUrl;
import com.example.have_you_eaten.bean.UserRegister;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 注册界面
 */
public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private static final String USER = "普通用户";
    private static final String BUSINESS = "商家";
    private static final String DELIVER = "骑手";

    private Button userBtn;
    private Button deliverBtn;
    private Button acceptBtn;
    private Button backBtn;
    private EditText usernameET, nameET, password1ET, password2ET, phoneET, idenNumET;
    private String userType;
    private MHandler mHandler = new MHandler();


    private void initAfterChoose() {
        backBtn = findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.register);
            }
        });

        acceptBtn = findViewById(R.id.btn_accept);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordValid(password1ET.getText().toString()) && !nameET.getText().toString().isEmpty()) {
                    if (isSamePsd(password1ET.getText().toString(), password2ET.getText().toString())) {
                        if (isTelphoneValid(phoneET.getText().toString())) {
                            UserRegister userRegister = new UserRegister();
                            userRegister.setUsername(usernameET.getText().toString());
                            userRegister.setPassword(password1ET.getText().toString());
                            userRegister.setName(nameET.getText().toString());
                            userRegister.setPhone(phoneET.getText().toString());
                            userRegister.setType(userType);
                            // 用户注册
                            if (idenNumET == null) {
                                // 服务端验证
                                asyncValidate(userRegister);
                            } else {
                                String idenNum = idenNumET.getText().toString();
                                if (isIDCard(idenNum)) {
                                    userRegister.setIdenNum(idenNum);
                                    asyncValidate(userRegister);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "身份证格式错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "电话格式错误", Toast.LENGTH_SHORT).show();
                            Log.e("erro", "Telephone erro");
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "两次密码不相同", Toast.LENGTH_SHORT).show();
                        Log.e("erro", "No same password");
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "密码格式错误或用户名为空", Toast.LENGTH_SHORT).show();
                    Log.e("erro", "Password");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mHandler = new MHandler();

        userBtn = findViewById(R.id.btn_register_user);
        Button businessBtn = findViewById(R.id.btn_register_business);
        deliverBtn = findViewById(R.id.btn_register_deliverer);

        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.user_register);
                usernameET = findViewById(R.id.usrAccount);
                nameET = findViewById(R.id.usrName);
                password1ET = findViewById(R.id.usrPwd);
                password2ET = findViewById(R.id.usrPwd2);
                phoneET = findViewById(R.id.usrPhone);
                setOnFocusChangeErrMsg(usernameET, "username");
                setOnFocusChangeErrMsg(nameET, "name");
                setOnFocusChangeErrMsg(password1ET, "password");
                setOnFocusChangeErrMsg(phoneET, "phone");
                password2ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        String pwd2 = password2ET.getText().toString();
                        String pwd = password1ET.getText().toString();
                        if (!b) {
                            if (isSamePsd(pwd, pwd2)) {
                                password2ET.setError(null);
                            } else {
                                password2ET.setError("两次输入密码不相同");
                            }
                        }
                    }
                });
                initAfterChoose();
                userType = USER;
            }
        });


        businessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.business_register);
                usernameET = findViewById(R.id.businessAccount);
                nameET = findViewById(R.id.businessName);
                password1ET = findViewById(R.id.businessPwd);
                password2ET = findViewById(R.id.businessPwd2);
                phoneET = findViewById(R.id.businessPhone);
                idenNumET = findViewById(R.id.businessIdenNum);

                setOnFocusChangeErrMsg(usernameET, "username");
                setOnFocusChangeErrMsg(nameET, "name");
                setOnFocusChangeErrMsg(password1ET, "password");
                setOnFocusChangeErrMsg(phoneET, "phone");
                setOnFocusChangeErrMsg(idenNumET, "idenNum");
                password2ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        String pwd2 = password2ET.getText().toString();
                        String pwd = password1ET.getText().toString();
                        if (!b) {
                            if (isSamePsd(pwd, pwd2)) {
                                password2ET.setError(null);
                            } else {
                                password2ET.setError("两次输入密码不相同");
                            }
                        }
                    }
                });
                initAfterChoose();
                userType = BUSINESS;
            }
        });

        deliverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.deliverer_register);
                //绑定
                usernameET = findViewById(R.id.delivererAccount);
                nameET = findViewById(R.id.delivererName);
                password1ET = findViewById(R.id.delivererPwd);
                password2ET = findViewById(R.id.delivererPwd2);
                phoneET = findViewById(R.id.delivererPhone);
                idenNumET = findViewById(R.id.delivererIdenNum);

                setOnFocusChangeErrMsg(usernameET, "username");
                setOnFocusChangeErrMsg(nameET, "name");
                setOnFocusChangeErrMsg(password1ET, "password");
                setOnFocusChangeErrMsg(phoneET, "phone");
                setOnFocusChangeErrMsg(idenNumET, "idenNum");
                password2ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        String pwd2 = password2ET.getText().toString();
                        String pwd = password1ET.getText().toString();
                        if (!b) {
                            if (isSamePsd(pwd, pwd2)) {
                                password2ET.setError(null);
                            } else {
                                password2ET.setError("两次输入密码不相同");
                            }
                        }
                    }
                });
                initAfterChoose();
                userType = DELIVER;
            }
        });


    }


    /**
     * 设置报错信息
     *
     * @param editText
     * @param inputType
     */
    private void setOnFocusChangeErrMsg(final EditText editText, final String inputType) {
        editText.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        String inputStr = editText.getText().toString();
                        if (!hasFocus) {
                            if (inputType.equals("name") || inputType.equals("username")) {
                                if (inputStr.isEmpty()) {
                                    editText.setError("不可为空");
                                } else {
                                    editText.setError(null);
                                }
                            }
                            if (inputType.equals("phone")) {
                                if (isTelphoneValid(inputStr)) {
                                    editText.setError(null);
                                } else {
                                    editText.setError("手机号格式不正确");
                                }
                            }
                            if (inputType.equals("password")) {
                                if (isPasswordValid(inputStr)) {
                                    editText.setError(null);
                                } else {
                                    editText.setError("密码不少于8位");
                                }
                            }
                            if (inputType.equals("idenNum")) {
                                if (isIDCard(inputStr)) {
                                    editText.setError(null);
                                } else {
                                    editText.setError("身份证格式错误");
                                }
                            }
                        }
                    }
                }
        );
    }

    /**
     * 与后端交互
     *
     * @param userRegister
     */
    private void asyncValidate(final UserRegister userRegister) {
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        // 2、构建请求体requestBody
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        // TODO userRegister
        RequestBody requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(userRegister));

        // 3、发送请求
        Request request = new Request.Builder()
                // TODO
                .url(ApiUrl.REGISTER_URL)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            /**
             * 请求错误的处理
             * @param call
             * @param e
             */
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                Log.e(TAG, "获取数据失败 ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // TODO 处理后端返回值：res是一个json数据
                /**
                 * {}
                 */

                String res = response.body().string();
                //打印服务端返回结果
                Log.i(TAG, "注册信息: " + res);
                // 构造并发送消息給UI线程
                Message msg = new Message();
                msg.what = MHandler.REGISTER_SUCCESS;
                msg.obj = res;
                mHandler.sendMessage(msg);
            }
        });
    }

    /**
     * 处理各种外部请求消息
     */
    class MHandler extends Handler {
        private static final int REGISTER_SUCCESS = 1;

        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case REGISTER_SUCCESS:
                    Log.v(TAG, "REGISTER_SUCCESS");
                    RegisterActivity.this.registerSuccess();
                    break;
            }
        }
    }

    /**
     * 注册成功弹窗
     */
    private void registerSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("注册成功");
        builder.setMessage("欢迎使用！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }

    /**
     * 校验手机号
     *
     * @param account
     * @return
     */
    private boolean isTelphoneValid(String account) {
        if (account == null) {
            return false;
        }
        // 首位为1, 第二位为3-9, 剩下九位为 0-9, 共11位数字
        String pattern = "^[1]([3-9])[0-9]{9}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(account);
        return m.matches();
    }

    /**
     * 校验密码不少于8位
     *
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 7;
    }

    /**
     * 校验第二次密码是否与第一次匹配
     *
     * @param password1
     * @param password2
     * @return
     */
    private boolean isSamePsd(String password1, String password2) {
        return password1.equals(password2);
    }

    /**
     * 校验身份证号
     *
     * @param IDCard
     * @return
     */
    private boolean isIDCard(String IDCard) {
        if (IDCard != null) {
            String IDCardRegex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x|Y|y)$)";
            return IDCard.matches(IDCardRegex);
        }
        return false;
    }
}
