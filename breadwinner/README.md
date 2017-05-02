# breadwinner
Do you wonder what to eat for your lunch/dinner today?

Don't you remember your meals in recent days?

Would you balance your food ingredients taking care for example to eat a fish twice a week?

Also I have often such concerns.
#

I created this web application to manage these reflections gracefully by tracking such information in database. 

**To run this application**

First edit the file:
`breadwinner/src/main/resources/hibernate.cfg`
to modify your database access parameters.

**Note:** *For Windows turn all below slashes "/" into backslashes "\".*

Then type the following commands:
```
cd breadwinner
mvn package
```

Deploy resulting `breadwinner/target/breadwinner-1.1.war` file to your web server.
Next in your browser go to url:
`http://localhost:8080/breadwinner-1.1/`

First define subsequent dinner components on "Dinner Components" tab by clicking "Add new Dinner Component" button.

Next go to "Dinners" tab, click "New dinner" button and fill in the data of your first lunch/dinner. Repeat every day to maintain consistent records of your meals.

To modify/clone the existing record just click it in the choosen row of the corresponding table.
