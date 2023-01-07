const express = require("express");
const router = express.Router();
const Item = require("../models/Item");


router.get("/", async (req, res) => {
    try{
        const posts = await Item.find();
        res.json(posts);
    }catch(err){
        res.json({message: err});
    }
});

router.post("/", async (req, res) => {
    const item = new Item({
        name: req.body.name,
        price: req.body.price,
        inventory: req.body.inventory
    })
    try{
        const savedItem = await item.save(); //??
        res.json(savedItem);
    }catch(err){
        res.json({message: err});
    }
});

router.delete("/:itemid", async (req, res) => {
    try{
    const removedItem = await Item.remove({_id: req.params.itemid});
    res.json(removedItem);
    }catch(err){
        res.json({message: err});
    }
});

router.patch("/:itemid", async (req, res) => {
    try{
        const updatedItem = await Item.updateOne(
            {_id: req.params.itemid},
            {$set: {inventory: req.body.inventory}},
            {$set: {price: req.body.price}}
        );
        res.json(updatedItem);
    }catch(err){
        res.json({message: err});
    }
});

module.exports = router;