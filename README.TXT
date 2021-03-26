1. Steps to Build and Test
   To build project - mvn clean install
   Application url = http://localhost:8080
   
2. Database Detail
   Database = h2(Inmemory database)
   Schema detail = classpath: schema.sql, data.sql
   Constraint: One account can associate with multiple address.
               Address will be valid only if country matches from country table.
               
  
3. Api Detail
   3.1 Save account api: POST /app/v1/account/ HTTP/1.1
                     Host: localhost:8080
                     Content-Type: application/json
                     Content-Length: 247
   Payload:
            {
                "firstName": "John",
                "lastName": "Doe",
                "emailAddress": "john.doe@gmail.com",
                "address": {
                    "street": "1234 Given Lane",
                    "city": "newyork2",
                    "province_state": "ON",
                    "country": "United State"
                }
            }
   Response: 
            {
              "dto": {
                  "id": 1,
                  "firstName": "John",
                  "lastName": "Doe",
                  "emailAddress": "john.doe@gmail.com",
                  "address": {
                      "id": 1,
                      "street": "1234 Given Lane",
                      "city": "newyork2",
                      "province_state": "ON",
                      "country": "United State"
                  }
              },
              "errorMessages": null
          }
  
   3.2 Get Account api: GET /app/v1/account/1 HTTP/1.1
                    Host: localhost:8080
       Response:
                 {
                    "dto": {
                        "id": 1,
                        "firstName": "John",
                        "lastName": "Doe",
                        "emailAddress": "john.doe@gmail.com",
                        "addressDtoList": [
                            {
                                "id": 1,
                                "street": "1234 Given Lane",
                                "city": "Toronto",
                                "province_state": "ON",
                                "country": "Canada"
                            },
                            {
                                "id": 2,
                                "street": "1234 Given Lane",
                                "city": "newyork2",
                                "province_state": "ON",
                                "country": "United State"
                            }
                        ]
                    },
                    "errorMessages": null
                }
                    
4. Use Case
   4.1 Account will be created with valid address(if country exist).
   4.2 Address will be updated if existing account(Unique emailAddress) try to insert same address(with same country name).
   4.3 New Address will be added if existing account(Unique emailAddress) try to add new address(with differnet country name)
                    
                    

   
   
