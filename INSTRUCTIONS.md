# Implementation  Details

## Launch Instructions
* _Run the application -> Navigate to http://localhost:8080/  -> Authenticate (admin01 and admin01pw) -> Try out the endpoints from swagger._

## Search API Contract

* Libraries Used:
 	* rsql-parser: parsing the input query into RSQL expression.
	* rsql-jpa: converts RSQL expression to JPA Criteria Query.
* The search query can be specified as a query parameter `_q`
* URL Scheme : **/v1/drivers/search?_q=driver(\<driver specs here\>)\<Joining Operator\>car(\<car specs here\>)**

For example,

|Query        | Description         |
|----------------|---------------------|
| ?_q=driver(username==driver*);car(color=\=Blue)| finds drivers with username like driver% AND associated with blue car|
| ?_q=driver(username==driver*)| finds drivers with username like driver% |
| ?_q=driver(onlineStatus==ONLINE)| finds all online drivers |
| ?_q=car(color==Blue)| finds drivers associated with blue car|

**NOTE: The specifications should match DO properties and values are case sensitive**

### Sample Request:

Request URL(**Authentication required**): http://localhost:8080/v1/drivers/search?_q=driver(username%3D%3Ddriver*)%3Bcar(color%3D%3DBlue)
Returns:
```
[
  {
    "id": 6,
    "username": "driver06",
    "password": "driver06pw"
  }
]
```

### FIQL Operators

|Operator        | Description         |
|----------------|---------------------|
| ==             | Equal To            |
| !=             | Not Equal To        |
| =gt=           | Greater Than        |
| =ge=           | Greater Or Equal To |
| =lt=           | Less Than           |
| =le=           | Less Or Equal To    |
| =in=           | In                  |
| =out=          | Not in              |



| Joining Operator  | Description         |
|--------------------|---------------------|
| ;                  | Logical AND         |
| ,                  | Logical OR          |

---

## Security Details

* Mechanism: Spring Security with JPA Authentication.
* http://localhost:8080/ (home controller) redirects you to login page (/login) and which forwards the user to swagger-ui after succesful authentication. All logged in users are also redirected to swagger-ui.
* Any existing driver credentials or the admin users added in data.sql can be used for authentication. For example,
	* driver01 and driver01pw
	* admin01 and admin01pw
* URL protection:
	* "/v1/drivers" is accessible by ADMIN and DRIVER.
	* "/v1/cars" is accessible only by ADMIN.
	* "/" and "/login" are world accessible.

---

Many Thanks!
❤️ ANKUR GOEL

---