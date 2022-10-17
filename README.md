To run the app go to TestTaskGeniuseeApplicationTests class and run main() method.

After the app started you have these endpoints available:

### MovieController:

**POST** ```localhost:8080/movies``` creates a movie;

**GET** ```localhost:8080/movies/{id}``` gets movie by id;

**PUT** ```localhost:8080/movies/{id}``` updates movie by id;

**DELETE** ```localhost:8080/movies/{id}``` deletes movie by id;

**GET** ```localhost:8080/movies?params``` gets movies according to criteria query. 

Possible variants of criteria: ```cinemaHallNumberIn={}```, ```titleIn={}```. 

This method also supports pagination: ```count=20&page=0```.

### OrderController:

**POST** ```localhost:8080/orders``` creates an order;

**GET** ```localhost:8080/orders/{id}``` gets order by id;

**PUT** ```localhost:8080/orders/{id}``` updates order by id;

**DELETE** ```localhost:8080/orders/{id}``` deletes order by id;

Sample body to create movie: 
```
{
  "title": "BestMovie2022",
  "description": "4real",
  "cinemaHallNumber": 1,
  "showTime": "2022-10-20T00:00:00"
}
```

Sample body to create order: 
```
{
  "movieIds": [1,2],
  "comment": "Best comment ever"
}
```

To check the objects were created and saved to DB you can go to 

```localhost:8080/h2-console```.


