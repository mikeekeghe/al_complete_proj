package Config;

/**
 * Created by Rajesh Dabhi on 19/7/2017.
 */

public class BaseURL {

    public static String PARM_RESPONCE = "responce";
    public static String PARM_ERROR = "error";
    public static String PARM_DATA = "data";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    public static final String PUSH_NOTIFICATION = "pushNotification";


    public static final String PREFS_NAME = "CarondealLoginPrefs";

    public static final String IS_LOGIN = "isLogin";

    public static final String KEY_NAME = "user_fullname";

    public static final String KEY_EMAIL = "user_email";

    public static final String KEY_ID = "user_id";

    public static final String KEY_TYPE_ID = "user_type_id";

    public static final String KEY_MOBILE = "user_phone";

    public static final String KEY_BDATE = "user_bdate";

    public static final String KEY_STATUS = "user_status";

    public static final String KEY_CITY = "user_city";

    public static final String KEY_CITY_NAME = "user_city_name";

    public static final String KEY_COUNTRY = "user_country";

    public static final String KEY_STATE = "user_state";

    public static final String KEY_IMAGE = "user_image";

    public static final String KEY_CURRENCY = "currency";


    public static String BASE_URL = "http://31.220.50.55/carsapp/";


    public static String IMG_SLIDER_URL = BASE_URL + "uploads/admin/sliders/";

    public static String IMG_POST_URL = BASE_URL + "uploads/image/";

    public static String IMG_PROFILE_URL = BASE_URL + "uploads/admin/profile/";

    public static String IMG_CATEGORY_URL = BASE_URL + "uploads/admin/category/";


    public static String GET_SLIDER = BASE_URL + "index.php/api/get_sliders";

    public static String GET_CATEGORIES = BASE_URL + "index.php/api/get_categories";

    public static String GET_POST = BASE_URL + "index.php/api/get_post";

    public static String GET_POST_RENT = BASE_URL + "index.php/api/get_rent_post";

    public static String LOGIN_URL = BASE_URL + "index.php/api/login";

    public static String REGISTER_URL = BASE_URL + "index.php/api/registration";

    public static String GET_CITY_URL = BASE_URL + "index.php/api/get_city";

    public static String ADD_POST_URL = BASE_URL + "index.php/api/add_post";

    public static String ADD_POST_IMAGE_URL = BASE_URL + "index.php/api/add_post_images";

    public static String UPDATE_PROFILE_PIC_URL = BASE_URL + "index.php/api/update_profile_pic";

    public static String UPDATE_PROFILE_URL = BASE_URL + "index.php/api/update_user_profile";

    public static String CHANGE_PASSWORD_URL = BASE_URL + "index.php/api/change_password";

    public static String REGISTER_FCM_URL = BASE_URL + "index.php/api/register_fcm";

    public static String GET_ATTRIBUTE_URL = BASE_URL + "index.php/api/get_attribute";

    public static String GET_POST_BY_CITY_URL = BASE_URL + "index.php/api/get_post_by_city";

    public static String GET_POST_BY_USER_URL = BASE_URL + "index.php/api/get_post_by_user_id";

    public static String DELETE_POST_URL = BASE_URL + "index.php/api/delete_post_by_id";

    public static String EDIT_POST_URL = BASE_URL + "index.php/api/edit_add_post_description";

    public static String UPDATE_POST_IMAGE_URL = BASE_URL + "index.php/api/update_post_image";

    public static String GET_POST_IMAGE_URL = BASE_URL + "index.php/api/get_image_by_post_id";

    public static String ADD_POST_ATTRIBUTE_URL = BASE_URL + "index.php/api/set_post_attribute";

    public static String GET_POST_ATTRIBUTE_URL = BASE_URL + "index.php/api/get_attribute_by_post";

    public static String EDIT_POST_ATTRIBUTE_URL = BASE_URL + "index.php/api/update_post_attribute";

    public static String ADD_REVIEW_URL = BASE_URL + "index.php/api/add_review";

    public static String GET_REVIEW_URL = BASE_URL + "index.php/api/get_review";

    public static String GET_JOIN_USER_URL = BASE_URL + "index.php/api/get_join_user";

    public static String GET_JOIN_DATA_URL = BASE_URL + "index.php/api/get_join_data";

    public static String ADD_CHAT_URL = BASE_URL + "index.php/api/send_chat_data";

    public static String GET_CHAT_DATA_BY_USER_URL = BASE_URL + "index.php/api/get_chat_data_by_user";

    public static String GET_BOOKMARK_URL = BASE_URL + "index.php/api/get_bookmarklist";

    public static String TERMS_URL = BASE_URL + "index.php/api/terms";

    public static String ABOUT_US_URL = BASE_URL + "index.php/api/aboutus";

    public static String GET_POST_SEARCH_URL = BASE_URL + "index.php/api/get_post_search";

    public static String GET_POST_SEARCH_BRAND_URL = BASE_URL + "index.php/api/get_post_search_brand";

    public static String GET_POST_SEARCH_YEAR_URL = BASE_URL + "index.php/api/get_post_search_year";

    public static String FORGOT_URL = BASE_URL + "index.php/api/forgot_password";

    public static String DELETE_REVIEW_URL = BASE_URL + "index.php/api/delete_review";

    public static String GET_NOTIFICATION_URL = BASE_URL + "index.php/api/get_notificationlist";

}
