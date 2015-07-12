/**
 * Created by kunalsingh.k on 11/07/15.
 */

var http_server = require("http");
var mongo = require('mongodb');
var express = require('express');
var router = express.Router();
var app = express();

var data;



http_server.createServer(function(request, response){
    response.writeHead(200);

    //response.write("This is the expense manager"+ data.toString());


    var mongodb = require('mongodb');
    var MongoClient = mongodb.MongoClient;
    var url = 'mongodb://localhost:27017/expense_manager';

    console.log(request.url);
    if(request.url.indexOf('/transaction_new?expenditure') > -1) {

            console.log("new transaction");
        MongoClient.connect(url, function (err, db) {
            if (err) {
                console.log('Unable to connect to the mongoDB server. Error:', err);
            } else {

                console.log(decodeURIComponent(request.url.substring(request.url.indexOf("expenditure=") + 12)));

                var expenditure = decodeURIComponent(request.url.substring(request.url.indexOf("expenditure=") + 12));

                expenditure = JSON.parse(expenditure);

                var collection = db.collection('transaction');
                expenditure["transaction_id"] = Math.random()* 1000000000000000000;
                expenditure['logged_by'] = "kunal.exuberant@gmail.com";
                // Insert some users
                collection.insert(expenditure, function (err, result) {
                    if (err) {
                        console.log(err);
                    } else {
                        //console.log('Inserted %d documents into the "users" collection. The documents inserted with "_id" are:', result.length, result);
                    }
                    db.close();
                });

            }
        });

        data = {
            "friends": [
                {
                    "name": "Kunal Singh",
                    "userid": "1905",
                    "balance": "-1200"
                },
                {
                    "name": "Ankit Singh",
                    "userid": "1925",
                    "balance": "-1240"
                },
                {
                    "name": "Ajit Singh",
                    "userid": "1944",
                    "balance": "1200"
                },
                {
                    "name": "Rohit Saxena",
                    "userid": "1205",
                    "balance": "-1230"
                }
            ]
        };
    }
    else {

        console.log("old transaction");
        MongoClient.connect(url, function (err, db) {
            if (err) {
                console.log('Unable to connect to the mongoDB server. Error:', err);
            } else {
                //console.log('Connection established to', url);

                var collection = db.collection('transaction');

                collection.find({logged_by: 'kunal.exuberant@gmail.com'}).toArray(function (err, result) {
                    if (err) {
                        console.log(err);
                    } else if (result.length) {
                        console.log('Found:', result);
                        data = result;
                    } else {
                        console.log('No document(s) found with defined "find" criteria!');
                    }
                });
                db.close();
            }
        });

        //response.setHeader('Content-Type', 'application/json');
        // response.send();

        data = {
            "friends": [
                {
                    "name": "Ankit Singh",
                    "userid": "1905",
                    "balance": "-200"
                },
                {
                    "name": "Kunal Singh",
                    "userid": "1925",
                    "balance": "2400"
                },
                {
                    "name": "Ajit Singh",
                    "userid": "1944",
                    "balance": "600"
                },
                {
                    "name": "Rohit Saxena",
                    "userid": "1205",
                    "balance": "-100"
                }
            ]
        };
    }

    response.end(JSON.stringify(data));

}).listen(8080);

console.log("Listening to port 8080");






