# Insertion of square
curl -i -X POST -H "Content-Type: application/json" -d '{"id":1, "name":"Read","minPoint":{"x":1,"y":2}, "maxPoint":{"x":3,"y":4}}' http://localhost:8080/api/square


curl -i -X GET -H "Content-Type: application/json" http://localhost:8080/api/squares

# Testing 
ShapeServiceTest class contains one method named 'testWhenThereIsIntersection'
to asses whether a given square intersected with the rest or not thereafter it 
saves it to the db or not.

# Test Coverage
The coverage is based on class %80 compared to 53% on lines  

#Dockerize
mvn clean package
docker build -t sentrworks/javageometry .
docker run -p 8080:8080 sentrworks/javageometry

#Swagger - Open API self documentation

http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
