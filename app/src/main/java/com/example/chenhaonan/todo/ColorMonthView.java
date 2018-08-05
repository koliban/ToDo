package com.example.chenhaonan.todo;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

public class ColorMonthView {

    public class ColorfulMonthView extends MonthView {

        private int mRadius;

        public ColorfulMonthView(Context context) {
            super(context);

            //兼容硬件加速无效的代码
            setLayerType(View.LAYER_TYPE_SOFTWARE, mSelectedPaint);
            //4.0以上硬件加速会导致无效
            mSelectedPaint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.SOLID));

            setLayerType(View.LAYER_TYPE_SOFTWARE, mSchemePaint);
            mSchemePaint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.SOLID));
        }

        @Override
        protected void onPreviewHook() {
            mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        }

        @Override
        protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
            int cx = x + mItemWidth / 2;
            int cy = y + mItemHeight / 2;
            canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
            return true;
        }

        @Override
        protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
            int cx = x + mItemWidth / 2;
            int cy = y + mItemHeight / 2;
            canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
        }

        @Override
        protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
            int cx = x + mItemWidth / 2;
            int top = y - mItemHeight / 8;
            if (isSelected) {
                canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                        mSelectTextPaint);
                canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mSelectedLunarTextPaint);
            } else if (hasScheme) {
                canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                        calendar.isCurrentDay() ? mCurDayTextPaint :
                                calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

                canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mSchemeLunarTextPaint);
            } else {
                canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                        calendar.isCurrentDay() ? mCurDayTextPaint :
                                calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
                canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mCurMonthLunarTextPaint);
            }
        }
    }

}
