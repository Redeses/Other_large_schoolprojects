const express = require("express");
const router = express.Router();
const User = require("../models/User");


router.get("/", async (req, res) => {
    try{
        const posts = await User.find();
        res.json(posts);
    }catch(err){
        res.json({message: err});
    }
});

router.post("/", async (req, res) => {
    const user = new User({
        username: req.body.username,
        password: req.body.password,
        userType: req.body.userType
    })
    try{
        const savedPost = await user.save(); //??
        res.json(savedPost);
    }catch(err){
        res.json({message: err});
    }
});

router.delete("/:userid", async (req, res) => {
    try{
    const removedUser = await User.remove({_id: req.params.userid});
    res.json(removedUser);
    }catch(err){
        res.json({message: err});
    }
});

module.exports = router;