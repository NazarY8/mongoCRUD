# Simple CRUD 🚀️ application which is used next technologies:

`reactive mongo` -> DB connection

`http4s` and `http4s-blaze-server` -> REST approach and local server

`MacWire` -> DI approach

`pure config` -> read .yaml files

`circe` and `http4s-circe` -> for .json format

# How to run the app?

Just use simple `sbt run` and then use `8080` port on `localhost`

P.S. Check if mongoDB is install on your mac -> `ps -ef | grep mongod | grep -v grep | wc -l | tr -d ' '` or `mongod --version` if not then you should install mongoDB:

1. Install and Update `brew` on macOS.
2. Install any version on mongoDB or just print on terminal -> `brew install mongodb-community@5.0`
3. Run mongoDB on your mac, from terminal -> `brew services start mongodb-community@5.0`

# How to work with the app?

In general, the idea of the task is quite simple, it was necessary to create **5 endpoints**, 4 of which are simple CRUD operations and one additional pagination. In addition, basic validation for Create should have been added. In my case, I created basic validation for `/create` and `/pagination` (look at `getAll` and `createBook` methods in BookController.

After running the app, just use `Postman` or any other app from which you can send requests to the server. As an alternative, I can suggest using CURL from any Terminal, like `curl -X POST 'http://localhost:8080/book/create?title=The%20Great%20Gatsby&author=F.%20Scott%20Fitzgerald'`

Postam examples:[](https://)

[](https://)Create/POST -> `http://localhost:8080/book/create?&title=ONE&author=ONE`

READ/GET -> `http://localhost:8080/book/read?id=644fa8a830181a49d950b5f5` (do not forget to put real book id)

UPDATE/PUT -> `http://localhost:8080/book/update?id=644fa8a830181a49d950b5f5&title=updTitle&author=updAuthor` (do not forget to put real book id)

DELETE/DELETE -> `http://localhost:8080/book/delete?id=644fa4a630181a49d950b3b5` (do not forget to put real book id)

Pagination/GetAll/GET -> `http://localhost:8080/book/pagination?page=1&limit=2` (do not forget to put for example 10 books, and use for example page=4, limit=3 as a result on page4 you should see only 1 book (3pages*3rows =9), 10 - 9 = 1 result on 4 pages.

# **Several images example:**
