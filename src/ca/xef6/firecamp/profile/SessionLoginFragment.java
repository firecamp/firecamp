/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.xef6.firecamp.profile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ca.xef6.firecamp.R;

import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

public class SessionLoginFragment extends Fragment {
    private static final String URL_PREFIX_FRIENDS = "https://graph.facebook.com/me/friends?access_token=";

    private TextView textInstructionsOrLink;
    private LoginButton buttonLoginLogout;
    private ProfilePictureView profilePictureView;
    private Session.StatusCallback statusCallback = new SessionStatusCallback();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.fragment, container, false);

	buttonLoginLogout = (LoginButton) view.findViewById(R.id.buttonLoginLogout);
	textInstructionsOrLink = (TextView) view.findViewById(R.id.instructionsOrLink);
	profilePictureView = (ProfilePictureView) view.findViewById(R.id.profilepic);

	Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

	Session session = Session.getActiveSession();
	if (session == null) {
	    if (savedInstanceState != null) {
		session = Session.restoreSession(getActivity(), null, statusCallback, savedInstanceState);
	    }
	    if (session == null) {
		session = new Session(getActivity());
	    }
	    Session.setActiveSession(session);
	    if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
		session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
	    }
	}

	updateView(session);
	try {
	    PackageInfo info = getActivity().getPackageManager().getPackageInfo("ca.xef6.firecamp", PackageManager.GET_SIGNATURES);
	    for (Signature signature : info.signatures) {
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(signature.toByteArray());
		Log.w("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	    }
	} catch (NameNotFoundException e) {

	} catch (NoSuchAlgorithmException e) {

	}
	return view;
    }

    @Override
    public void onStart() {
	super.onStart();
	Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    public void onStop() {
	super.onStop();
	Session.getActiveSession().removeCallback(statusCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
	super.onSaveInstanceState(outState);
	Session session = Session.getActiveSession();
	Session.saveSession(session, outState);
    }

    private void updateView(Session session) {
	// Session session = Session.getActiveSession();
	if (session.isOpened()) {

	    textInstructionsOrLink.setText(URL_PREFIX_FRIENDS + session.getAccessToken());
	    buttonLoginLogout.setText("Logout");
	    buttonLoginLogout.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
		    onClickLogout();
		}
	    });
	} else {
	    textInstructionsOrLink.setText("instructions");
	    buttonLoginLogout.setText("login");
	    buttonLoginLogout.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
		    onClickLogin();
		}
	    });
	}
    }

    private void onClickLogin() {
	Session session = Session.getActiveSession();
	if (!session.isOpened() && !session.isClosed()) {
	    session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
	} else {
	    Session.openActiveSession(getActivity(), this, true, statusCallback);
	}
    }

    private void onClickLogout() {
	Session session = Session.getActiveSession();
	if (!session.isClosed()) {
	    session.closeAndClearTokenInformation();
	}
    }

    private class SessionStatusCallback implements Session.StatusCallback {
	@Override
	public void call(Session session, SessionState state, Exception exception) {
	    updateView(session);
	}
    }
}
