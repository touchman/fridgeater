# fridgeater

Have you ever wondered about cooking recipes?

Always checking you fridge and thinking about what to cook?

Don't worry, dude! Now we can just put name of our stuff from fridge into this API and it provides you different cook recipes!

---

for loading data into database:

curl --header "Content-Type: application/json" \
  --request POST \
  --data @recipes.json \
  http://localhost:8090/fridgeater/rest/recipe/recipes

recipes.json -- from backend\src\main\resources\recipes.json