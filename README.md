# __About:__
This project implemented via `Servlets`.

There are Servlets for each method such as: `findAll, findBiId, add, update, delete`

Implemented:
 * `Pagination for findAll method`
 * `Filters`
 * `Receipt output in pdf format`
 * `Ability to fill the database through the request (optional)` 
___
# __Project setup steps__
___
* ```git clone <username>/servlet-project.git ```
* go to the folder ```cd 'your project folder'```
* paste project url from the first step
* open the project in your IDE ```File->Open->'your project folder'```

# __To ```run``` application you need:__

* open folder with project in the terminal ```cd 'your project folder'```
* enter ```gradle clean build```
* get the database up in Docker by command ```docker compose up -d --build```
* run application from your IDE using Tomcat

___
# __Steps for work with application:__

__Work with database migration:__
* If you want to fill database you need go to ```GET.../migration``` endpoint
___
__Work with products:__
* For getting all products from database you should go to ```GET.../products?limit=<your desired limit>&offset=<your desired offset>```
* For add product to the database you should go to ```POST.../product/add``` 
and pass the following parameters in the body of the request:
   ```
   {
   "title": <Product title>,
   "price": <Product price>,
   "discount": true/false
   } 
  ```
  `*If you have made migrration and full the database, adding new product could throw an exception.
  Clean up the database before adding new items`
* For update information about products you should go to ```POST.../poduct/add?id=<id of product to be updated>```
and pass the following parameters in the body of the request:
  ```
   {
   "title": <Product title>,
   "price": <Product price>,
   "discount": true/false
   } 
   ```
* For get product from the database using product's id you should go to ```GET.../product?id=<product's id in the db>```
* For delete product from the database you should go to  ```DELETE.../product/delete?id=<product's id in the db>```
___
__Work with discount cards:__
* For getting all cards from database you should go to ```GET.../cards?limit=<your desired limit>&offset=<your desired offset>```
* For add card to the database you should go to ```POST.../card/add``` 
and pass the following parameters in the body of the request:
   ```
   {
   "number": <card number>,
   "discount": true/false
   } 
  ```
  `*If you have made migrration and full the database, adding new card could throw an exception.
    Clean up the database before adding new items`
* For update information about cards you should go to ```POST.../card/add?id=<id of card to be updated>```
and pass the following parameters in the body of the request:
   ```
  {
  "number": <card number>,
  "discount": true/false
  } 
  ```
* For get card from the database using card's id you should go to ```GET.../card?id=<card's id in the db>```
* For delete card from the database you should go to  ```DELETE.../card/delete?id=<card's id in the db>```
___
__Work with discount receipt:__
* For get receipt from the database you should go to ```GET.../receipt?id=<receipt's id in the db>```
* For add new receipt to the database you should go to ```POST.../receipt```
and pass the following parameters in the body of the request:
```
  {
     "products" :[ 
      {
         "id": <product id>,
         "title": <String product title>,
         "price": <BigDecimal price>,
         "discount": <boolean discount>
     },
     {
         "id": <product id>,
         "title": <String product title>,
         "price": <BigDecimal price>,
         "discount": <boolean discount>
     }
     ],
     "card": {
         "id": <card id>,
         "number": <Integer number>,
         "discount": <boolean discount>
     } 
  }
  ```
* You can add as many products as required.
* Adding a discount card is optional, as it is used to get a discount on promotional products.
* You can download the receipt in the Pdf format just paste ```GET.../card?id=<card's id in the db>```
in browser.

`*By default receipts aren't filled in during migration.
It means you should fill it by yourself using Postman or any others tools you prefer`