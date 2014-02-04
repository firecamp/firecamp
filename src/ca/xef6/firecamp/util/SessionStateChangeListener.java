package ca.xef6.firecamp.util;

import com.facebook.Session;
import com.facebook.SessionState;

public interface SessionStateChangeListener {
    void onSessionStateChange(Session session, SessionState sessionState, Exception exception);
}
