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

P.S.S. By default reactiveMongo should create needed DB and Collection if you don't have it, however, if something goes wrong, you can look at the database options in the `config.yaml` file and create the required database and collection manually.

# How to work with the app?

In general, the idea of the task is quite simple, it was necessary to create **5 endpoints**, 4 of which are simple CRUD operations and one additional pagination. In addition, basic validation for Create should have been added. In my case, I created basic validation for `/create` and `/pagination` (look at `getAll` and `createBook` methods in BookController.

After running the app, just use `Postman` or any other app from which you can send requests to the server. As an alternative, I can suggest using CURL from any Terminal, like `curl -X POST 'http://localhost:8080/book/create?title=The%20Great%20Gatsby&author=F.%20Scott%20Fitzgerald'`

Postam examples:[](https://)

[](https://)Create/POST -> `http://localhost:8080/book/create?&title=ONE&author=ONE`

READ/GET -> `http://localhost:8080/book/read?id=644fa8a830181a49d950b5f5` (do not forget to put real book id)

UPDATE/PUT -> `http://localhost:8080/book/update?id=644fa8a830181a49d950b5f5&title=updTitle&author=updAuthor` (do not forget to put real book id)

DELETE/DELETE -> `http://localhost:8080/book/delete?id=644fa4a630181a49d950b3b5` (do not forget to put real book id)

Pagination/GetAll/GET -> `http://localhost:8080/book/pagination?page=1&limit=2` (do not forget to put for example 10 books, and use for example page=4, limit=3 as a result on page4 you should see only 1 book (3pages*3rows =9), 10 - 9 = 1 result on 4 pages.

# **Several images:**

### Create Book:
![createBook](https://user-images.githubusercontent.com/73239084/235467885-4b63d934-34a4-4d9d-8e4e-4801d89e4478.jpg)

### Read Book:
![readBook](https://user-images.githubusercontent.com/73239084/235467806-14c25760-b0e6-4ec9-bc71-ee22771620d2.jpg)

### Update Book:
![updateBook](https://user-images.githubusercontent.com/73239084/235467918-ad5bae91-cfe4-4520-9bbc-b7923180346d.jpg)

### Delete Book:
![deleteBook](https://user-images.githubusercontent.com/73239084/235467944-cd78688f-6c1a-436f-b601-752ca911393b.jpg)

### Pagination:
![pagination](https://user-images.githubusercontent.com/73239084/235467848-289c0e94-9247-451a-ad74-897e6ac4756a.jpg)

### Create Book Validation example:
![createValidation](https://user-images.githubusercontent.com/73239084/235467979-55a2b67a-aa79-44c1-88ae-f949530b61ad.jpg)

### Pagination Book Validation examples:
![pagination_with_default_value](https://user-images.githubusercontent.com/73239084/235468293-2ba2ee50-d5e5-4618-9a95-fe59e9470ccb.jpg)

![pagination_without_parameter](https://user-images.githubusercontent.com/73239084/235468296-f42d8820-fc52-48cb-8e63-8e2e19550571.jpg)




