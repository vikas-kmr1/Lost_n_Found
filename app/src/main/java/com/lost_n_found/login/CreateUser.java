package com.lost_n_found.login;

import com.google.firebase.database.IgnoreExtraProperties;

import javax.annotation.Nullable;

// [START rtdb_user_class]
    @IgnoreExtraProperties
    public class CreateUser {
        public String uid;
        public String username;
        public String email;
        public String phone;
        public String enrollment_no;
        public @Nullable  String avatar;

        public CreateUser(){
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }


        public CreateUser(String uid, String username, String email,@Nullable String avatar) {
            this.username = username;
            this.email = email;
            this.uid = uid;
            this.avatar = avatar;
            this.phone = "N/A";
            this.enrollment_no = "N/A";
        }


    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEnrollment_no() {
        return enrollment_no;
    }

    @Nullable
    public String getAvatar() {
        return avatar;
    }


    }

