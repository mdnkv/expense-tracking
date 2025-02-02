## Test data

The application comes with some mock data that you can use. There are 5 mock users that have some data populated (such as accounts and operations). All data was created using [FakeNameGenerator.com](https://fakenamegenerator.com) and [Email Generator](https://generator.email/) websites and these are users are not real people!

Test data is integrated into the project using migrations. This means that you do not need to complete any additional steps in order to import it! Just use any of these email accounts to log in to the app:

- el96grafiti@auhit.com
- tysonusarmy@trimart.xyz
- chrihurs@likebaiviet.com
- tdstasa@nelcoapps.com
- osamu0926@shopcobe.com

All users have the password ```secret1234```.

If you do not use any mock data to be imported, just remove the ```src/main/resources/db/migration/V2__testdata.sql``` file before running the application against the database.
