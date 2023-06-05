package io.genemoz.custombottomnav;

import android.content.Context;

public class NavUtils {
    public static float d2p(Context context, float dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }

}
