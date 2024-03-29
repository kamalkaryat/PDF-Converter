package com.ksk.image2pdfconverter.util;

import android.annotation.TargetApi;
import android.app.Activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ksk.image2pdfconverter.R;

/**
 * !! IMPORTANT !!
 * permission arrays are defined in Constants.java file. we have two types of permissions:
 * READ_WRITE_PERMISSIONS and READ_WRITE_CAMERA_PERMISSIONS 
 * use these constants in project whenever required.
 */
public class PermissionsUtils {

    private static class SingletonHolder {
        static final PermissionsUtils INSTANCE = new PermissionsUtils();
    }

    public static PermissionsUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * checkRuntimePermissions takes in an Object instance(can be of type Activity or Fragment),
     * an array of permission and checks for if all the permissions are granted ot not
     *
     * @param context     can be of type Activity or Fragment
     * @param permissions string array of permissions
     * @return true if all permissions are granted, otherwise false
     */
    public boolean checkRuntimePermissions(Object context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if ((ContextCompat.checkSelfPermission(retrieveContext(context),
                        permission)
                        != PackageManager.PERMISSION_GRANTED)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * requestRuntimePermissions takes in an Object instance(can be of type Activity or Fragment),
     * a String array of permissions and
     * a permission request code and requests for the permission
     *
     * @param context     can be of type Activity or Fragment
     * @param permissions string array of permissions
     * @param requestCode permission request code
     */
    @TargetApi(Build.VERSION_CODES.O_MR1)
    public void requestRuntimePermissions(Object context, String[] permissions,
                                                 int requestCode) {
        if (context instanceof Activity) {
            ActivityCompat.requestPermissions((AppCompatActivity) context,
                    permissions, requestCode);
        } else if (context instanceof Fragment) {
            ((Fragment) context).requestPermissions(permissions, requestCode);
        }
    }

    /**
     * retrieves context of passed in non-null object, context can be of type
     * AppCompatActivity or Fragment
     *
     * @param context     can be of type AppCompatActivity or Fragment
     */
    private Context retrieveContext(@NonNull Object context) {
        if (context instanceof AppCompatActivity) {
            return ((AppCompatActivity) context).getApplicationContext();
        } else {
            return ((Fragment) context).requireActivity();
        }
    }

    /**
     * Handle a RequestPermissionResult by checking if the first permission is granted
     * and executing a Runnable when permission is granted
     * @param grantResults the GrantResults Array
     * @param requestCode
     * @param expectedRequest
     * @param whenSuccessful the Runnable to call when permission is granted
     */
    public void handleRequestPermissionsResult(Activity context, @NonNull int[] grantResults,
                                               int requestCode, int expectedRequest, @NonNull Runnable whenSuccessful) {
        if (requestCode != expectedRequest)
            return;
        if (grantResults.length < 1) {
            StringUtils.getInstance().showSnackbar(context, R.string.snackbar_insufficient_permissions);
            return;
        }
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            whenSuccessful.run();
            StringUtils.getInstance().showSnackbar(context, R.string.snackbar_permissions_given);
        } else
            StringUtils.getInstance().showSnackbar(context, R.string.snackbar_insufficient_permissions);
    }
}
