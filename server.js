/**
 * Created by kunalsingh.k on 11/07/15.
 */

var http_server = require("http");

http_server.createServer(function(request, response){
    response.writeHead(200);
    response.write("This is the espense manager server");
    response.end("Close the connection");
}).listen(8080);

console.log("Listening to port 8080");
