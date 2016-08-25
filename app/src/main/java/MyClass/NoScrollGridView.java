package MyClass;

import android.view.View;
import android.widget.GridView;

/**
 * Created by qiuhong on 8/25/16.
 */
public class NoScrollGridView extends GridView {

    public NoScrollGridView (android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}
