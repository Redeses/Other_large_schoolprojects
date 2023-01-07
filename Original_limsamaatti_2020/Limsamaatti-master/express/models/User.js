const mongoose = require("mongoose");

const UserSchema = mongoose.Schema({
    username: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    },
    userType: { //Basic user, admin or company
        type: Number,
        required: true
    }
});

module.exports = mongoose.model("Users", UserSchema);