package com.musicstreet.mobile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by Owner on 4/29/2015.
 */
public class SettingFragment extends Fragment {

    private View rootView;
    private static final int YES_NO_CALL = 10;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting,container,false);
        final TextView t = (TextView) rootView.findViewById(R.id.PremiumText);
        final Button subbutton = (Button) rootView.findViewById(R.id.subscriptionbutton);

        final ParseUser p = ParseUser.getCurrentUser();

        final ErrorDialog delyes = getPrompt("Goodbye", ":(", new Fragment(){public void onActivityResult(int req, int res, Intent data)
        {super.onActivityResult(req,res,data);}});
        final ErrorDialog delno = getPrompt("Mismatch", "Passwords do not match!", new Fragment(){public void onActivityResult(int req, int res, Intent data)
        {super.onActivityResult(req,res,data);}});
        final Button delbutton = (Button) rootView.findViewById(R.id.deleteaccbutton);
        delbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getYesNoPrompt("Delete Your account??","We'd be sorry to see you go. Are you sure?",new Fragment(){
                    @Override
                    public void onActivityResult(int req, int res, Intent data)
                    {
                        if(res == Activity.RESULT_OK)
                        {
                            try {
                                if(!ProConnectSettings.deleteAccount(p.getUsername(),((EditText)rootView.findViewById(R.id.pwtext1)).getText().toString(),((EditText)rootView.findViewById(R.id.pwText2)).getText().toString()))
                                {
                                    delno.show(getFragmentManager(), "");
                                }
                                else
                                {
                                    delyes.show(getFragmentManager(), "");
                                }
                            } catch (Exception e) {
                                getErrorPrompt(e, new Fragment(){public void onActivityResult(int req, int res, Intent data)
                                {super.onActivityResult(req,res,data);}}).show(getFragmentManager(), "");
                            }
                        }
                    }}).show(getFragmentManager(), "");
            }
        });
        final Button emailupdatebutton = (Button) rootView.findViewById(R.id.emailupdatebutton);
        final EditText eAddresstField = (EditText)rootView.findViewById(R.id.emailAddressTextField);

        eAddresstField.setText(p.getEmail());
        emailupdatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ProConnectSettings.updateEmail(eAddresstField.getText().toString(), eAddresstField.getText().toString());
                } catch (Exception e) {
                    getErrorPrompt(e, new Fragment(){public void onActivityResult(int req, int res, Intent data)
                    {super.onActivityResult(req,res,data);}}).show(getFragmentManager(), "");
                }

            }

        });
        final CheckBox b = (CheckBox) rootView.findViewById(R.id.emailCheckbox);
        b.setChecked(p.getBoolean("emailNotifications"));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ProConnectSettings.changeEmailNotifications(b.isChecked());
                } catch (ParseException e) {
                    getErrorPrompt(e, new Fragment(){public void onActivityResult(int req, int res, Intent data)
                    {super.onActivityResult(req,res,data);}}).show(getFragmentManager(), "");
                }

            }

        });
        final Button pwupdatebutton = (Button) rootView.findViewById(R.id.pwbutton);
        pwupdatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ProConnectSettings.updatePassword();
                    getPrompt("Email Sent","We've sent an email to the email you've provided that provides a password update.", new Fragment(){public void onActivityResult(int req, int res, Intent data)
                    {super.onActivityResult(req,res,data);}}).show(getFragmentManager(), "");

                } catch (ParseException e) {
                    getErrorPrompt(e, new Fragment(){public void onActivityResult(int req, int res, Intent data)
                    {super.onActivityResult(req,res,data);}}).show(getFragmentManager(), "");
                }

            }

        });

        refresh(t, subbutton, p.getBoolean("premiumSubscription"));
        subbutton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            public void onClick(View v) {
                // Perform action on click
                if(!ParseUser.getCurrentUser().getBoolean("premiumSubscription")) {

                    DialogFragment dialog =
                            getYesNoPrompt("Purchasing Premium","Are you sure you would like to purchase premium? Monthly charges to the account you provided will begin.",
                                    new Fragment(){
                                        @Override
                                        public void onActivityResult(int req, int res, Intent data)
                                        {
                                            if(res == Activity.RESULT_OK)
                                            {
                                                try {
                                                    ProConnectSettings.changeSubStatus(true);
                                                    refresh(t, subbutton, true);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                    });

                    dialog.show(getFragmentManager(), "tag");
                }
                else
                {
                    getYesNoPrompt("Leaving Premium?","Are you sure you want to leave? Valuable user data to your company tied to the features you pay for monthly may end up being deleted.",
                            new Fragment(){
                                @Override
                                public void onActivityResult(int req, int res, Intent data)
                                {
                                    if(res == Activity.RESULT_OK)
                                    {
                                        try {
                                            ProConnectSettings.changeSubStatus(false);

                                            refresh(t, subbutton, false);
                                        } catch (ParseException e) {
                                            getErrorPrompt(e, new Fragment(){public void onActivityResult(int req, int res, Intent data)
                                            {super.onActivityResult(req,res,data);}}).show(getFragmentManager(), "");
                                        }
                                    }
                                }

                            }).show(getFragmentManager(), "tag");
                }
            }
        });
        return rootView;
    }

    private void refresh(TextView t, Button subbutton, boolean isP) {
        if(isP)
        {
            t.setText(getString(R.string.Premiumtexttrue));
            subbutton.setText(getString(R.string.PremiumButtonTextTrue));
        }
        else
        {
            t.setText(getString(R.string.getpremiumpromotion));
            subbutton.setText(getString(R.string.Premiumtexttrue));
        }
    }

    /**
     * produces an ErrorDialog given some variables. uses the ClickListener for compatibility with a previous build of the software.
     * @param c context of the application
     * @param e error to pull message from
     * @param q ClickListener with method to call on exit
     * @return an ErrorDialog matching all of these properties
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static ErrorDialog getErrorPrompt(Context c, Exception e, final DialogInterface.OnClickListener q)
    {
        ErrorDialog dialog = new ErrorDialog();
        Bundle args = new Bundle();
        args.putString("title", "Error");
        args.putString("message", e.getMessage());
        dialog.setArguments(args);
        dialog.setTargetFragment(new Fragment(){
            @Override
            public void onActivityResult(int req, int res, Intent data)
            {
                if(q != null)
                    q.onClick(null,0);
            }

        }, YES_NO_CALL);

        return dialog;
    }

    /**
     * Provides a yes or no prompt lining up to the parameters provided to it. Helper class to place most of the inner details of fragments in separate methods where I as a coder don't have to think about them.
     * @param title title for the prompt
     * @param message message for the prompt
     * @param f a fragment with an onActivityResult method for code to run given a yes and no response from the user.
     * @return a YesNoDialog prompt that responds as the parameters specify
     */
    public static YesNoDialog getYesNoPrompt(String title, String message,Fragment f)
    {
        YesNoDialog dialog = new YesNoDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        dialog.setArguments(args);
        dialog.setTargetFragment(f, YES_NO_CALL);
        return dialog;
    }

    /**
     * provides an ErrorPrompt based on e and q. A better version of the previous method.
     * @param e error to take the message from
     * @param q Fragment containing methods to call on exit conditions
     * @return Error dialog based on these properties
     */
    public static ErrorDialog getErrorPrompt(Exception e, Fragment q)
    {
        ErrorDialog dialog = new ErrorDialog();
        Bundle args = new Bundle();
        args.putString("title", "Error");
        args.putString("message", e.getMessage());
        dialog.setArguments(args);
        dialog.setTargetFragment(q, YES_NO_CALL);

        return dialog;
    }
    public static ErrorDialog getPrompt(String title, String message, Fragment q)
    {
        ErrorDialog dialog = new ErrorDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        dialog.setArguments(args);
        dialog.setTargetFragment(q, YES_NO_CALL);

        return dialog;
    }
    /**
     * A class used to generate a dialog that matches up with an error.
     * Likely to be replaced with Toast, a predesigned suite to handle these sort of situations.
     */
    public static class ErrorDialog extends DialogFragment
    {
        public ErrorDialog()
        {

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            Bundle args = getArguments();
            String title = (String)args.get("title");
            String message = (String)args.get("message");
            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setMessage(message)
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                        }
                    })
                    .create();
        }
    }
    /**
     * This code is taken from StackOverflow to learning how fragments work through practical example
     * http://stackoverflow.com/questions/7977392/android-dialogfragment-vs-dialog/21032871#21032871
     * This class provides a dialog with yes and no options.
     */
    public static class YesNoDialog extends DialogFragment
    {
        public YesNoDialog()
        {

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            Bundle args = getArguments();
            String title = (String) args.get("title");
            String message = (String) args.get("message");

            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                        }
                    })
                    .create();
        }
    }



}
