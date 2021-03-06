package com.swaas.kangle.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.swaas.kangle.R;

/**
 * Created by Sayellessh on 10-05-2017.
 */

public class CustomFontTextView extends TextView{

    public CustomFontTextView(Context context) {
        super(context);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
            String fontName = a.getString(R.styleable.CustomFontTextView_fontName);
            if (fontName!=null) {
                Typeface typeFace = TypeFaces.getTypeFace(context, "fonts/roboto/" + fontName);
                setTypeface(typeFace);
            }
            a.recycle();
        }
    }
}
