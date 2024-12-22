package com.example.readify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TermCondition extends AppCompatActivity {

    TextView terms;
    Button agreeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_term_condition);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        terms=findViewById(R.id.terms_content);
        agreeButton=findViewById(R.id.agree);

        String termsText = "Welcome to Readify!\n\n" +
                "Effective Date: 12/08/2024\n" +
                "Last Updated: 12/08/2024\n\n" +
                "By downloading, installing, or using this application, you agree to the following terms and conditions. Please read them carefully.\n\n" +

                "1. Acceptance of Terms\n\n" +
                "By accessing or using Readify, you agree to abide by these Terms and Conditions and any applicable laws. If you do not agree, please refrain from using the application.\n\n" +

                "2. User Responsibilities\n\n" +
                "1. You are responsible for the security of your account credentials and any activity under your account.\n" +
                "3. You agree to use the app solely for personal, non-commercial purposes unless authorized by Readify in writing.\n\n" +

                "3. Privacy Policy\n\n" +
                "1. Data Collection: Readify collects minimal personal information necessary for app functionality, such as user preferences, bookmarks, and reading history.\n" +
                "2. Data Usage: Your data will be used to improve the app’s functionality and user experience. It will not be sold or shared with third parties without your explicit consent.\n" +
                "3. Data Security: We take reasonable measures to protect your data but cannot guarantee complete security.\n\n" +
                "For more details, please refer to our Privacy Policy.\n\n" +

                "4. Intellectual Property\n\n" +
                "1. All content within the Readify app, including logos, graphics, and user interface elements, is owned by Readify and protected by copyright laws.\n" +
                "2. You may not duplicate, distribute, or create derivative works from any content in the app without prior written consent.\n\n" +

                "5. User-Generated Content\n\n" +
                "1. You are responsible for ensuring that any content you upload, such as custom PDFs, notes, or bookmarks, does not infringe on copyright, intellectual property rights, or violate any laws.\n" +
                "2. By uploading content, you grant Readify a non-exclusive, worldwide license to store and display that content within the app.\n\n" +

                "6. Prohibited Activities\n\n" +
                "1. Misuse of the app, including but not limited to:\n" +
                "- Reverse engineering the app.\n" +
                "- Hacking or modifying its source code.\n" +
                "- Using the app for illegal activities.\n" +
                "2. Uploading or sharing prohibited content, including:\n" +
                "- Content that violates intellectual property laws.\n" +
                "- Harmful or malicious files.\n\n" +
                "Violation of these terms may result in termination of access to the app.\n\n" +

                "7. Updates and Modifications\n\n" +
                "1. Readify reserves the right to update or modify the app at any time without prior notice.\n" +
                "2. Continued use of the app after updates implies acceptance of the revised terms.\n\n" +

                "8. Limitations of Liability\n\n" +
                "1. No Warranty: Readify is provided “as-is” without warranties of any kind, either express or implied.\n" +
                "2. Liability: Readify is not responsible for any loss, damage, or harm resulting from the use of the app, including data loss or unauthorized access.\n" +
                "3. Third-Party Links: Readify may contain links to external websites. We are not responsible for the content, privacy, or security of these third-party services.\n\n" +

                "9. Termination\n\n" +
                "Readify reserves the right to suspend or terminate your access to the app at its sole discretion, without prior notice, if you violate these terms.\n\n" +

                "10. Governing Law\n\n" +
                "These terms shall be governed and construed in accordance with the laws of [Your Country/Region]. Any disputes arising from these terms will be subject to the jurisdiction of the courts in [Your Location].\n\n" +

                "11. Contact Us\n\n" +
                "If you have any questions about these terms, please contact us:\n" +
                "Email: tawhidul.hasan401@gmail.com\n" +
                "Phone: 01326503869\n\n" +

                "By clicking “I Agree” or using the Readify app, you confirm that you have read, understood, and accepted these Terms and Conditions.";

        terms.setText(termsText);

        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TermCondition.this,Login.class);
                startActivity(intent);
            }
        });
    }
}