package MyClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cetcme.zytyumin.R;

/**
 * Created by qiuhong on 8/25/16.
 */
public class ListSeparator extends LinearLayout {
    public ListSeparator(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.list_separator, this, true);
    }
}
