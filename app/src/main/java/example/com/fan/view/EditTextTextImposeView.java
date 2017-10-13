package example.com.fan.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by lian on 2017/10/13.
 */
public class EditTextTextImposeView {
    private static int num = 30;
    private static EditText etNoteContent;
    private static TextView tvWordNumber;

    public EditTextTextImposeView(EditText editText, TextView tvWordNumber) {
        this.etNoteContent = editText;
        this.tvWordNumber = tvWordNumber;
    }

    public static void ImPoseText() {
//etNoteContent是EditText
        etNoteContent.addTextChangedListener(new TextWatcher() {
            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;//实时记录输入的字数
            }

            @Override
            public void afterTextChanged(Editable s) {
//                int number = num - s.length();
                //TextView显示剩余字数
                tvWordNumber.setText("" + s.length() + "/" + num);
                selectionStart = etNoteContent.getSelectionStart();
                selectionEnd = etNoteContent.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etNoteContent.setText(s);
                    etNoteContent.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
    }
}
