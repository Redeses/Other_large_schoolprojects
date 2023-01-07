const express = require("express");
const mongoose = require("mongoose");
const app = express();
const bodyParser = require("body-parser")
const cors = require("cors");

app.use(bodyParser.json());
app.use(cors());

//Import routes
const usersRoute = require("./routes/users");
const itemsRoute = require("./routes/items")

app.use("/users", usersRoute);
app.use("/items", itemsRoute);

app.get("/", (req, res) => {
    res.send("Home");
});

var db = 'mongodb://localhost:27017';
mongoose.connect(db,
{ useNewUrlParser: true }, () =>
    function(err) {
        if (err) {
        console.log("Unable to connect to Mongo.");
        process.exit(1);
        }
    },
    console.log("Connected to db")
  );

  app.listen(3001);