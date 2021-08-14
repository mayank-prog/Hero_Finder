package com.hero.finder.herofinder;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class HeroAbout extends AppCompatActivity {

    Button download;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_about);

        pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset("read.pdf").load();
        download = findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
    }

    private void download() {
           storageReference=firebaseStorage.getInstance().getReference();
           ref = storageReference.child("HeroFinder.pdf");

           ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
               @Override
               public void onSuccess(Uri uri) {
                   String url = uri.toString();
                   downloadFile(HeroAbout.this,"HeroFinder",".pdf",DIRECTORY_DOWNLOADS,url);
                   Toast.makeText(getApplicationContext(),"Downloading.....",Toast.LENGTH_LONG).show();

               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(getApplicationContext(),"check your internet connectivity",Toast.LENGTH_LONG).show();
               }
           });
    }

    private void downloadFile(Context context,String fileName,String fileExtension,String destinationDirectory,String url) {

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,fileName+fileExtension);
        downloadManager.enqueue(request);

    }
}