package com.realestate.vendeedor;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class upho extends Fragment {


    public upho() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        }
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Button upload;
    int Image_Request_Code = 7;
    TextView tt;
    boolean rr=false;
    ProgressDialog progressDialog ;
    Uri FilePathUri;
    EditText name,address,city,pincode,prize,squarfoot,bedroom,bathroom,discription;
    RadioGroup radioSexGroup,radioSexGroup1;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String uploaaaa="";
    RadioButton radioSexButton,radioSexButton1;
    Button b1;
    String alsopath="";
View v5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v5=inflater.inflate(R.layout.fragment_upho, container, false);

        b1=(Button)v5.findViewById(R.id.submit);
        name=(EditText)v5.findViewById(R.id.name);
        tt=(TextView)v5.findViewById(R.id.uplo);
        address=(EditText)v5.findViewById(R.id.address);
        upload=(Button)v5.findViewById(R.id.upload);
        prize=(EditText)v5.findViewById(R.id.prize);
        pincode=(EditText)v5.findViewById(R.id.pincode);
        city=(EditText)v5.findViewById(R.id.city);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        progressDialog = new ProgressDialog(v5.getContext());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");
        squarfoot=(EditText)v5.findViewById(R.id.squarefoot);
        bedroom=(EditText)v5.findViewById(R.id.bedroom);
        bathroom=(EditText)v5.findViewById(R.id.bathroom);
        discription=(EditText)v5.findViewById(R.id.discription);
        radioSexGroup=(RadioGroup)v5.findViewById(R.id.rorsell);
        radioSexGroup1=(RadioGroup)v5.findViewById(R.id.parking);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().equals(""))
                {
                    name.setError("Required filled");
                }
                else if(address.getText().toString().equals(""))
                {
                    address.setError("Required filled");
                }
                else if(city.getText().toString().equals(""))
                {
                    city.setError("Required filled");
                }
                else if(pincode.getText().toString().equals(""))
                {
                    pincode.setError("Required filled");
                }
                else if(prize.getText().toString().equals(""))
                {
                    prize.setError("Required filled");
                }else if(squarfoot.getText().toString().equals(""))
                {
                    squarfoot.setError("Required filled");
                }else if(bedroom.getText().toString().equals(""))
                {
                    bedroom.setError("Required filled");
                }
                else if(bathroom.getText().toString().equals(""))
                {
                    bathroom.setError("Required filled");
                }
                else if(uploaaaa.equals(""))
                {
                    Toast.makeText(v5.getContext(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
                }
                else
                {
                    UploadImageFileToFirebaseStorage();
                    final int[] ghj = {0};
                    int selectedId=radioSexGroup.getCheckedRadioButtonId();
                    radioSexButton=(RadioButton)v5.findViewById(selectedId);
                    int selectedId1=radioSexGroup1.getCheckedRadioButtonId();
                    radioSexButton1=(RadioButton)v5.findViewById(selectedId1);
                    final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    final DatabaseReference myRef1 = database.getReference("user").child(currentFirebaseUser.getUid()).child("totalads");
                    final String[] adname = {""};

                    myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            adname[0] = dataSnapshot.getValue(String.class);
                            ghj[0] =Integer.parseInt(adname[0]+"");
                            ghj[0]++;
                            adname[0]= ghj[0] +"";
                            myRef1.setValue(ghj[0] +"");
                            alsopath="ads"+adname[0];
                            myRef.child(currentFirebaseUser.getUid()).child("ads"+adname[0]).child("name").setValue(name.getText().toString());
                            myRef.child(currentFirebaseUser.getUid()).child("ads"+adname[0]).child("address").setValue(address.getText().toString());
                            myRef.child(currentFirebaseUser.getUid()).child("ads"+adname[0]).child("city").setValue(city.getText().toString());
                            myRef.child(currentFirebaseUser.getUid()).child("ads"+adname[0]).child("pincode").setValue(pincode.getText().toString());
                            myRef.child(currentFirebaseUser.getUid()).child("ads"+adname[0]).child("prize").setValue(prize.getText().toString());
                            myRef.child(currentFirebaseUser.getUid()).child("ads"+adname[0]).child("bedroom").setValue(bedroom.getText().toString());
                            myRef.child(currentFirebaseUser.getUid()).child("ads"+adname[0]).child("bathroom").setValue(bathroom.getText().toString());
                            myRef.child(currentFirebaseUser.getUid()).child("ads"+adname[0]).child("squarefoor").setValue(squarfoot.getText().toString());
                            myRef.child(currentFirebaseUser.getUid()).child("ads"+adname[0]).child("discription").setValue(""+discription.getText().toString());
                            myRef.child(currentFirebaseUser.getUid()).child("ads"+adname[0]).child("parking").setValue(radioSexButton1.getText());
                            myRef.child(currentFirebaseUser.getUid()).child("ads"+adname[0]).child("rentorsell").setValue(radioSexButton.getText());
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });



                }


            }
        });

        return v5;
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getActivity().getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);
                tt.setText(getFileName(data.getData())+"");
                upload.setText("Image Selected");
                uploaaaa="ty";
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void UploadImageFileToFirebaseStorage() {


        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Please Wait...");

            // Showing progressDialog.
            progressDialog.show();

            final StorageReference storageReference2nd = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Getting image name from EditText and store into string variable.
                            String TempImageName = "photos";
                            Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                            final String[] generatedFilePath = {""};
                            storageReference2nd.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // getting image uri and converting into string
                                    Uri downloadUrl = uri;
                                    generatedFilePath[0] = downloadUrl.toString();

                                    final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                    myRef.child(currentFirebaseUser.getUid()).child(alsopath).child("image").setValue(generatedFilePath[0] +"");

                                }
                            });

                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            // Toast.makeText(getApplicationContext(), "Successfully Publish", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            ImageUploadInfo imageUploadInfo = new ImageUploadInfo(TempImageName, taskSnapshot.getUploadSessionUri().toString());
                        }
                    })


                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(v5.getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Please Wait...");

                        }
                    });
        }
        else {
            Toast.makeText(v5.getContext(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
        }
    }

}
