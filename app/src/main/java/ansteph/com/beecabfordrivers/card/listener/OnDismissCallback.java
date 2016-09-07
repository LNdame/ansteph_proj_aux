package ansteph.com.beecabfordrivers.card.listener;

import android.support.annotation.NonNull;

import ansteph.com.beecabfordrivers.card.Card;

/**
 * Created by loicStephan on 06/09/16.
 */
public interface OnDismissCallback {
    /**
     * A Card is dismissed.
     *
     * @param card
     *         which is dismissed.
     * @param position
     *         where the Card is.
     */
    void onDismiss(@NonNull final Card card, int position);
}
