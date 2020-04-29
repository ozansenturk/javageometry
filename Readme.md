# Insertion of square
curl -i -X POST -H "Content-Type: application/json" -d '{"id":1, "name":"Read","minPoint":{"x":1,"y":2}, "maxPoint":{"x":3,"y":4}}' http://localhost:8080/api/square

curl -i -X POST -H "Content-Type: application/json" http://localhost:8080/api/square

#Testing 
Due to time constraint, I managed to provide only unit tests to asses
whether the squares intersected each other and the correspective test
is shared below

'testNoIntersectionWithSquare'

The concept is the following:

1- First square should be saved accordingly

2- After the first square, new squares to be saved should be 
tested with the ones already saved whether they are intersected or not


#Dockerize
mvn clean package
docker build -t sentrworks/javageometry .
docker run -p 8080:8080 sentrworks/javageometry
