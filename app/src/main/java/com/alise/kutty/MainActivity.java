package com.alise.kutty;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import android.util.Base64;

public class MainActivity extends Activity {
    private static final String PREFS = "alise_kutty_local_lock";
    private SharedPreferences prefs;
    private EditText passwordInput;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().setNavigationBarColor(Color.BLACK);
        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        showGate();
    }

    private GradientDrawable rounded(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color); d.setCornerRadius(radius); d.setStroke(1, Color.DKGRAY);
        return d;
    }

    private TextView text(String value, int size, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value); t.setTextColor(Color.WHITE); t.setTextSize(size);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        t.setGravity(Gravity.CENTER);
        return t;
    }

    private Button button(String label) {
        Button b = new Button(this); b.setText(label); b.setTextColor(Color.BLACK);
        b.setAllCaps(false); b.setTextSize(15); b.setBackground(rounded(Color.WHITE, 28));
        return b;
    }

    private LinearLayout base() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL); root.setGravity(Gravity.CENTER);
        root.setPadding(28, 28, 28, 28); root.setBackgroundColor(Color.rgb(8,8,8));
        return root;
    }

    private void addLogo(LinearLayout root) {
        ImageView logo = new ImageView(this); logo.setImageResource(R.drawable.anime_logo);
        logo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(190, 190);
        lp.bottomMargin = 22; logo.setBackground(rounded(Color.BLACK, 22));
        logo.setClipToOutline(true); root.addView(logo, lp);
    }

    private void showGate() {
        LinearLayout root = base(); addLogo(root);
        root.addView(text("Alise×Kutty", 27, true));
        boolean hasPassword = prefs.contains("hash") && prefs.contains("salt");
        root.addView(text(hasPassword ? "Enter your app password" : "Create your app password", 15, false));
        passwordInput = new EditText(this); passwordInput.setSingleLine(true);
        passwordInput.setTextColor(Color.WHITE); passwordInput.setHintTextColor(Color.GRAY);
        passwordInput.setHint("Password"); passwordInput.setInputType(129);
        passwordInput.setPadding(18, 14, 18, 14); passwordInput.setBackground(rounded(0xFF171717, 18));
        LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(-1, -2); ip.setMargins(0, 24, 0, 14);
        root.addView(passwordInput, ip);
        Button go = button(hasPassword ? "Unlock" : "Set password");
        root.addView(go, new LinearLayout.LayoutParams(-1, -2));
        TextView note = text("Your password is stored locally on this device.", 12, false);
        note.setTextColor(0xFFAAAAAA); LinearLayout.LayoutParams np = new LinearLayout.LayoutParams(-1, -2); np.topMargin = 18;
        root.addView(note, np);
        go.setOnClickListener(v -> {
            String value = passwordInput.getText().toString();
            if (value.length() < 6) { toast("Use at least 6 characters."); return; }
            if (hasPassword) {
                String salt = prefs.getString("salt", "");
                if (constantTimeEquals(prefs.getString("hash", ""), hash(value, salt))) showHome();
                else toast("Incorrect password.");
            } else {
                byte[] bytes = new byte[16]; new SecureRandom().nextBytes(bytes);
                String salt = Base64.encodeToString(bytes, Base64.NO_WRAP);
                prefs.edit().putString("salt", salt).putString("hash", hash(value, salt)).apply();
                showHome();
            }
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(passwordInput.getWindowToken(), 0);
        });
        setContentView(root);
    }

    private String hash(String password, String salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes("UTF-8"), 120000, 256);
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
            return Base64.encodeToString(result, Base64.NO_WRAP);
        } catch (Exception e) { return ""; }
    }

    private boolean constantTimeEquals(String a, String b) {
        return MessageDigest.isEqual(a.getBytes(), b.getBytes());
    }

    private void showHome() {
        LinearLayout root = base(); addLogo(root);
        root.addView(text("Alise×Kutty", 29, true));
        TextView sub = text("Welcome", 16, false); sub.setTextColor(0xFFBBBBBB);
        root.addView(sub);
        Button invite = button("Open Discord Community");
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(-1, -2); bp.topMargin = 28;
        root.addView(invite, bp);
        invite.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/4luv"));
            startActivity(i);
        });
        Button lock = button("Lock app"); LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2); lp.topMargin = 12;
        root.addView(lock, lp); lock.setOnClickListener(v -> showGate());
        setContentView(root);
    }

    private void toast(String message) { Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); }
}
