package techline.carsapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import io.paperdb.Paper;
import model.Post_image_model;
import util.CommonAsyTask;
import util.JSONParser;
import util.LocaleHelper;
import util.NameValuePair;

public class Add_post_galleryActivity extends CommonAppCompatActivity implements View.OnClickListener {

    private static String TAG = Add_post_galleryActivity.class.getSimpleName();

    private Button btn_continue;
    private TextView tv_persenteg1;
    private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5;
    private ImageView iv_gallery_1, iv_gallery_2, iv_gallery_3, iv_gallery_4, iv_gallery_5;

    private static final int GALLERY_REQUEST_CODE1 = 201;
    private static final int GALLERY_REQUEST_CODE2 = 202;
    private static final int GALLERY_REQUEST_CODE3 = 203;
    private static final int GALLERY_REQUEST_CODE4 = 204;
    private static final int GALLERY_REQUEST_CODE5 = 365;

    private String filePath_1 = "";
    private String filePath_2 = "";
    private String filePath_3 = "";
    private String filePath_4 = "";
    private String filePath_5 = "";

    private boolean file_1 = false;
    private boolean file_2 = false;
    private boolean file_3 = false;
    private boolean file_4 = false;
    private boolean file_5 = false;

    private String get_post_id;

    private Boolean exit = false;
    private Boolean isedit = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // title remove
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_add_post_gallery);
        Paper.init(this);

        String language = Paper.book().read("language");
        if (language == null) {
            Paper.book().write("language", "ar");
        }
        get_post_id = getIntent().getStringExtra("post_id");

        if (getIntent().getStringExtra("isedit") != null) {
            isedit = true;
        }

        btn_continue = (Button) findViewById(R.id.btn_add_gallery_continue);
        tv_persenteg1 = (TextView) findViewById(R.id.tv_add_gallery_percenteg1);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
        progressBar5 = (ProgressBar) findViewById(R.id.progressBar5);

        iv_gallery_1 = (ImageView) findViewById(R.id.iv_gallery_1);
        iv_gallery_2 = (ImageView) findViewById(R.id.iv_gallery_2);
        iv_gallery_3 = (ImageView) findViewById(R.id.iv_gallery_3);
        iv_gallery_4 = (ImageView) findViewById(R.id.iv_gallery_4);
        iv_gallery_5 = (ImageView) findViewById(R.id.iv_gallery_5);

        iv_gallery_1.setOnClickListener(this);
        iv_gallery_2.setOnClickListener(this);
        iv_gallery_3.setOnClickListener(this);
        iv_gallery_4.setOnClickListener(this);
        iv_gallery_5.setOnClickListener(this);

        btn_continue.setOnClickListener(this);

        if (get_post_id != null) {
            makeGetGalleryByPost(get_post_id);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (id == R.id.btn_add_gallery_continue) {

            if (isedit) {
                finish();
            } else {
                Intent feaureIntent = new Intent(Add_post_galleryActivity.this, Add_post_attributeActivity.class);
                feaureIntent.putExtra("post_id", get_post_id);
                startActivity(feaureIntent);
                finish();
            }


        } else if (id == R.id.iv_gallery_1) {
            // Start the gallery Intent
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE1);

        } else if (id == R.id.iv_gallery_2) {
            // Start the gallery Intent
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE2);

        } else if (id == R.id.iv_gallery_3) {
            // Start the gallery Intent
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE3);

        } else if (id == R.id.iv_gallery_4) {
            // Start the gallery Intent
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE4);

        } else if (id == R.id.iv_gallery_5) {
            // Start the gallery Intent
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE5);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (resultCode == RESULT_OK) {

            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                //filePath = imgDecodableString;

                Bitmap b = BitmapFactory.decodeFile(imgDecodableString);
                Bitmap out = Bitmap.createScaledBitmap(b, 1200, 1024, false);

                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);


                File file = new File(imgDecodableString);

                FileOutputStream fOut;
                try {
                    fOut = new FileOutputStream(file);
                    out.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                    fOut.flush();
                    fOut.close();
                    //b.recycle();
                    //out.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (requestCode == GALLERY_REQUEST_CODE1) {
                    // Set the Image in ImageView after decoding the String
                    iv_gallery_1.setImageBitmap(bitmap);
                    filePath_1 = file.getAbsolutePath();

                    //new UploadFileToServer("35", file.getAbsolutePath(), 1).execute();
                    if (file_1) {
                        new addGallery(get_post_id, file.getAbsolutePath(), 1, "front", true).execute();
                    } else {
                        new addGallery(get_post_id, file.getAbsolutePath(), 1, "front", false).execute();
                    }

                } else if (requestCode == GALLERY_REQUEST_CODE2) {
                    // Set the Image in ImageView after decoding the String
                    iv_gallery_2.setImageBitmap(bitmap);
                    filePath_2 = file.getAbsolutePath();

                    if (file_2) {
                        new addGallery(get_post_id, file.getAbsolutePath(), 2, "back", true).execute();
                    } else {
                        new addGallery(get_post_id, file.getAbsolutePath(), 2, "back", false).execute();
                    }
                } else if (requestCode == GALLERY_REQUEST_CODE3) {
                    // Set the Image in ImageView after decoding the String
                    iv_gallery_3.setImageBitmap(bitmap);
                    filePath_3 = file.getAbsolutePath();

                    if (file_3) {
                        new addGallery(get_post_id, file.getAbsolutePath(), 3, "inner", true).execute();
                    } else {
                        new addGallery(get_post_id, file.getAbsolutePath(), 3, "inner", false).execute();
                    }

                } else if (requestCode == GALLERY_REQUEST_CODE4) {
                    // Set the Image in ImageView after decoding the String
                    iv_gallery_4.setImageBitmap(bitmap);
                    filePath_4 = file.getAbsolutePath();

                    if (file_4) {
                        new addGallery(get_post_id, file.getAbsolutePath(), 4, "outer", true).execute();
                    } else {
                        new addGallery(get_post_id, file.getAbsolutePath(), 4, "outer", false).execute();
                    }
                } else if (requestCode == GALLERY_REQUEST_CODE5) {
                    // Set the Image in ImageView after decoding the String
                    iv_gallery_5.setImageBitmap(bitmap);
                    filePath_5 = file.getAbsolutePath();

                    if (file_5) {
                        new addGallery(get_post_id, file.getAbsolutePath(), 5, "other", true).execute();
                    } else {
                        new addGallery(get_post_id, file.getAbsolutePath(), 5, "other", false).execute();
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private class addGallery extends AsyncTask<String, Integer, Void> {

        JSONParser jsonParser;
        ArrayList<NameValuePair> nameValuePairs;
        boolean response, is_edit;
        String error_string, success_msg;
        String filePath = "";
        int imagenumber = 0;

        private addGallery(String post_id, String filepath, int imagenumber, String post_face, boolean is_edit) {

            nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new NameValuePair("post_id", post_id));
            nameValuePairs.add(new NameValuePair("post_face", post_face));

            this.filePath = filepath;
            this.imagenumber = imagenumber;
            this.is_edit = is_edit;

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            /*progressBar1.setProgress(100);
            progressBar2.setProgress(0);
            progressBar3.setProgress(0);
            progressBar4.setProgress(0);
            progressBar5.setProgress(0);

            progressBar1.setMax(100);
            progressBar2.setMax(100);
            progressBar3.setMax(100);
            progressBar4.setMax(100);*/

            jsonParser = new JSONParser(Add_post_galleryActivity.this);
        }

        protected Void doInBackground(String... urls) {

            String json_responce = null;
            try {

                if (is_edit) {
                    json_responce = jsonParser.execMultiPartPostScriptJSON(BaseURL.UPDATE_POST_IMAGE_URL,
                            nameValuePairs, "image/png", filePath, "image");
                } else {
                    json_responce = jsonParser.execMultiPartPostScriptJSON(BaseURL.ADD_POST_IMAGE_URL,
                            nameValuePairs, "image/png", filePath, "image");
                }

                Log.e(TAG, json_responce + "," + filePath);

                JSONObject jObj = new JSONObject(json_responce);
                if (jObj.getBoolean("responce")) {
                    response = true;
                    success_msg = jObj.getString("data");
                } else {
                    response = false;
                    error_string = jObj.getString("error");
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            /*tv_persenteg1.setText("test" + values[0] + "%");
            progressBar1.setProgress(values[0]);*/

            /*if (imagenumber == 1) {
            //tv_persenteg1.setText(values[0] + "%");
            progressBar1.setProgress(values[0]);
        } else if (imagenumber == 2) {
            progressBar2.setProgress(values[0]);
        } else if (imagenumber == 3) {
            progressBar3.setProgress(values[0]);
        } else if (imagenumber == 4) {
            progressBar4.setProgress(values[0]);
        }*/
        }

        protected void onPostExecute(Void result) {

            if (response) {

                if (is_edit) {
                    Toast.makeText(Add_post_galleryActivity.this, getResources().getString(R.string.image_succesfully_updated), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Add_post_galleryActivity.this, getResources().getString(R.string.image_succesfully_added), Toast.LENGTH_SHORT).show();
                }

                if (imagenumber == 1) {
                    filePath_1 = "";
                    file_1 = true;
                } else if (imagenumber == 2) {
                    filePath_2 = "";
                    file_2 = true;
                } else if (imagenumber == 3) {
                    filePath_3 = "";
                    file_3 = true;
                } else if (imagenumber == 4) {
                    filePath_4 = "";
                    file_4 = true;
                } else if (imagenumber == 5) {
                    filePath_5 = "";
                    file_5 = true;
                }

            } else {
                Toast.makeText(Add_post_galleryActivity.this, "" + error_string, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void makeGetGalleryByPost(String post_id) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("post_id", post_id));

        // CommonAsyTask class for load data from api and manage response and api
        CommonAsyTask task_post = new CommonAsyTask(params,
                BaseURL.GET_POST_IMAGE_URL, new CommonAsyTask.VJsonResponce() {
            @Override
            public void VResponce(String response) {
                Log.e(TAG, response);

                List<Post_image_model> post_image_modelList = new ArrayList<>();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Post_image_model>>() {
                }.getType();

                post_image_modelList = gson.fromJson(response, listType);

                if (!post_image_modelList.isEmpty()) {

                    for (int i = 0; i < post_image_modelList.size(); i++) {

                        // get existing data from server and set in imageview for gallery
                        if (post_image_modelList.get(i).getPost_face().equals("front")) {
                            Picasso.with(Add_post_galleryActivity.this)
                                    .load(BaseURL.IMG_POST_URL + post_image_modelList.get(i).getImage_path())
                                    .placeholder(R.mipmap.ic_launcher)
                                    .into(iv_gallery_1);
                            filePath_1 = "";
                            file_1 = true;
                        }

                        if (post_image_modelList.get(i).getPost_face().equals("back")) {
                            Picasso.with(Add_post_galleryActivity.this)
                                    .load(BaseURL.IMG_POST_URL + post_image_modelList.get(i).getImage_path())
                                    .placeholder(R.mipmap.ic_launcher)
                                    .into(iv_gallery_2);
                            filePath_2 = "";
                            file_2 = true;
                        }

                        if (post_image_modelList.get(i).getPost_face().equals("inner")) {
                            Picasso.with(Add_post_galleryActivity.this)
                                    .load(BaseURL.IMG_POST_URL + post_image_modelList.get(i).getImage_path())
                                    .placeholder(R.mipmap.ic_launcher)
                                    .into(iv_gallery_3);
                            filePath_3 = "";
                            file_3 = true;
                        }

                        if (post_image_modelList.get(i).getPost_face().equals("outer")) {
                            Picasso.with(Add_post_galleryActivity.this)
                                    .load(BaseURL.IMG_POST_URL + post_image_modelList.get(i).getImage_path())
                                    .placeholder(R.mipmap.ic_launcher)
                                    .into(iv_gallery_4);
                            filePath_4 = "";
                            file_4 = true;
                        }

                        if (post_image_modelList.get(i).getPost_face().equals("other")) {
                            Picasso.with(Add_post_galleryActivity.this)
                                    .load(BaseURL.IMG_POST_URL + post_image_modelList.get(i).getImage_path())
                                    .placeholder(R.mipmap.ic_launcher)
                                    .into(iv_gallery_5);
                            filePath_5 = "";
                            file_5 = true;
                        }

                    }

                }

            }

            @Override
            public void VError(String responce) {
                Log.e(TAG, responce);
            }
        }, true, this);
        task_post.execute();
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            this.finish();
        } else {
            Toast.makeText(this, getResources().getString(R.string.press_back_gallery),
                    Toast.LENGTH_SHORT).show();
            exit = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }

}
