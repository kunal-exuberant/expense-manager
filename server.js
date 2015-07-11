/**
 * Created by kunalsingh.k on 11/07/15.
 */

var http_server = require("http");
var mongo = require('mongodb');

http_server.createServer(function(request, response){
    response.writeHead(200);

    var data = {

        "user1":"Kunal singh",  "user2":"Ankit singh", "User 3" : "Ajit Singh", "user4": "Rohit Saxena"

    };

    //response.write("This is the expense manager"+ data.toString());


    var mongodb = require('mongodb');
    var MongoClient = mongodb.MongoClient;
    var url = 'mongodb://localhost:27017/expense_manager';

    MongoClient.connect(url, function (err, db) {
        if (err) {
            console.log('Unable to connect to the mongoDB server. Error:', err);
        } else {
            console.log('Connection established to', url);


            var collection = db.collection('users');

            //Create some users
            var user1 = {name: 'modulus admin', age: 42, roles: ['admin', 'moderator', 'user']};
            var user2 = {name: 'modulus user', age: 22, roles: ['user']};
            var user3 = {name: 'modulus super admin', age: 92, roles: ['super-admin', 'admin', 'moderator', 'user']};

            // Insert some users
            collection.insert([user1, user2, user3], function (err, result) {
                if (err) {
                    console.log(err);
                } else {
                    console.log('Inserted %d documents into the "users" collection. The documents inserted with "_id" are:', result.length, result);
                }
                db.close();
            });
        }
    });


    MongoClient.connect(url, function (err, db) {
        if (err) {
            console.log('Unable to connect to the mongoDB server. Error:', err);
        } else {
            console.log('Connection established to', url);

            var collection = db.collection('users');

            collection.find({name: 'modulus user'}).toArray(function (err, result) {
                if (err) {
                    console.log(err);
                } else if (result.length) {
                    console.log('Found:', result);
                } else {
                    console.log('No document(s) found with defined "find" criteria!');
                }

            });
            db.close();
        }
    });

    //response.setHeader('Content-Type', 'application/json');
   // response.send();

    response.end(JSON.stringify(data));

}).listen(8080);

console.log("Listening to port 8080");






