package ca.xef6.firecamp.people;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ca.xef6.firecamp.R;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

public class PeopleFragment extends Fragment {

    private View              view;
    private View              loginLayout;
    private View              peopleListLayout;

    private UiLifecycleHelper uiLifecycleHelper;

    private Session           session;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiLifecycleHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiLifecycleHelper = new UiLifecycleHelper(getActivity(), new Session.StatusCallback() {

            @Override
            public void call(Session session, SessionState state, Exception exception) {
                onSessionStateChange(session, state, exception);
            }

        });
        uiLifecycleHelper.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_people, container, false);
        loginLayout = view.findViewById(R.id.login_layout);
        peopleListLayout = view.findViewById(R.id.people_list_layout);
        updateView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiLifecycleHelper.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiLifecycleHelper.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        uiLifecycleHelper.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiLifecycleHelper.onSaveInstanceState(outState);
    }

    public void onSessionStateChange(Session session, SessionState state, Exception exception) {
        this.session = session;
        updateView();
    }

    @Override
    public void onStop() {
        super.onStop();
        uiLifecycleHelper.onStop();
    }

    private void updateView() {
        if (session != null && session.isOpened()) {
            peopleListLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
        } else {
            loginLayout.setVisibility(View.VISIBLE);
            peopleListLayout.setVisibility(View.GONE);
        }
    }

}