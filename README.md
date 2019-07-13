# fridgeater

Have you ever wondered about cooking recipes?

Always checking you fridge and thinking about what to cook?

Don't worry, dude! Now we can just put name of our stuff from fridge into this API and it provides you different cook recipes!

---

starting:

##cd fridgeater
##mvn clean install
##docker-compose up

###for loading data into database:

curl -H "Content-Type: application/json" \
  -X POST \
  -d "@backend\src\main\resources\recipes.json" \
  http://localhost/backend/recipe/recipes
  
  
 Use:
http://localhost/

to run SeleniumTests:

##mvn test -Dtest=SeleniumTest

to test loading context:

##mvn test -Dtest=FridgeaterApplicationTests

jen pas is 1735ee916abb41999f29936ce53860d6