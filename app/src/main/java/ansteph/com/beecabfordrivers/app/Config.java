package ansteph.com.beecabfordrivers.app;

/**
 * Created by loicStephan on 28/06/16.
 */
public class Config {



    /****************************************************---Domain---*************************************************************/

   // private static String DOMAIN="http://10.0.0.8:8888/taxi/";
    private static String DOMAIN="http://10.11.0.86:8888/taxi/";

    /****************************************************---Route---*************************************************************/


    public static final String URL_VERIFY_OTP = DOMAIN+"v1/activate_user_driver";
    public static final String REGISTER_URL = DOMAIN+"v1/register_driver";

    public static final String RETRIEVE_USER_URL =DOMAIN+"v1/retrievetduser/%s/%s";
    public static final String RETRIEVE_JOBS_URL =DOMAIN+"v1/retrieveallpendingjob";
    public static final String REGISTER_FB =DOMAIN+"v1/register_fbNot";

    public static final String CREATE_JOB_URL =DOMAIN+"v1/createjob";

    public static final String UPLOAD_URL =DOMAIN+"v1/save_driver_profile_image";
    public static final String IMAGES_URL = DOMAIN+"v1/retrieveallimage";
    public static final String UPLOAD_URL_image =DOMAIN+"v1/save_driver_profile";
    public static final String RETRIEVE_USER_IMAGE_URL = DOMAIN+"v1/retrieve_dr_profile_image/%s";
    public static final String RETRIEVE_USER_PROFILE_URL = DOMAIN+"v1/retrieve_dr_profile/%s";


    /****************************************************---SMS FLAGS---*************************************************************/

    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use  MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve MSG91 to get one
    public static final String SMS_ORIGIN = "ANSTEPHIM";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";

       // server URL configuration http://localhost:8888/taxi/v1/activate_user
    public static final String URL_REQUEST_SMS = "http://10.101.162.25:8888/android_sms/request_sms.php";




    /****************************************************---Params Keys---*************************************************************/





    //Keys to send username, password, phone and otp
    public static final String KEY_ID = "id";
    public static final String KEY_COMPANY_ID = "company_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_COMNAME = "company_name";
    public static final String KEY_MOBILE = "mobile";

    public static final String KEY_CARMODEL = "carmodel";
    public static final String KEY_NUMPLATE = "numplate";
    public static final String KEY_LICENSE = "licence";
    public static final String KEY_YEAR = "year";
    public static final String KEY_API = "apikey";
    public static final String KEY_PWD = "password";

    //Keys to send username, password, phone and otp

    public static final String KEY_PASSWORD = "password";


    public static final String KEY_OTP = "otp";

       //JSON Tag from response from server
    public static final String ERROR_RESPONSE= "error";
    public static final String MSG_RESPONSE= "message";

    public static final String DATA_URL = "http://www.simplifiedcodingreaders.16mb.com/superheroes.php";

    //Tag values to read from json
    public static final String TAG_IMAGE_URL = "car_picture_url";
    public static final String TAG_DRIVER_ID = "taxi_driver_id";

    public static final String KEY_CAR_MODEL = "car_model";
    public static final String KEY_CAR_NUMPLATE = "car_numberplate";
    public static final String KEY_PRO_ID= "taxi_driver_id";
    public static final String KEY_CURRENT_CITY = "current_city";
    public static final String KEY_PRO_RATING = "profile_rating";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


}