package com.hudhud.insouqapplication.AppUtils.Helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseManger<E> {
    private static  FirebaseManger firebaseManger;
    private DatabaseReference databaseReference;


    StorageReference storageReference;
    private FirebaseAuth mAuth;
    private static String key;
    public  Class<E> value;
    public static FirebaseManger getInstance() {
        if (firebaseManger == null) {
            firebaseManger = new FirebaseManger();
        }
        return  firebaseManger;
    }
    public void CastObject(Class<E> value) {
        this.value = value;
    }
    public  String getKey(){
        key =  databaseReference == null ? FirebaseDatabase.getInstance().getReference().push().getKey() : databaseReference.push().getKey();
        return  key;
    }

    //singelton
    public  StorageReference getstorageReference() {
        if (storageReference == null)
            storageReference = FirebaseStorage.getInstance().getReference();;
        return  storageReference;
    }
    public  DatabaseReference getDatabaseReference() {
        if (databaseReference == null)
            databaseReference = FirebaseDatabase.getInstance().getReference();
        return  databaseReference;
    }
public FirebaseAuth getmAuth(){
    if (mAuth == null)
        mAuth = FirebaseAuth.getInstance();
    return  mAuth;

}
    //generice
    public void getObjData(String nodeName , final CallBackFirebase callBackFirebase) {
        getDatabaseReference().child(nodeName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :dataSnapshot.getChildren())
                    callBackFirebase.itemDataSnapshot(snapshot.getValue(value));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

    public interface CallBackFirebase {
        void itemDataSnapshot(Object value);
    }

}
