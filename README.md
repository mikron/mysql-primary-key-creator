# mysql-primary-key-creator
## Install
~~~
mvn clean package
~~~
## Configuration
Configure `db.properties` file according to your environment
~~~
url=jdbc:mysql://localhost/integration?autoReconnect=true
user=root
password=o7DsCMP4XrFutcn8lFu9
~~~
## Run
~~~
java -jar ../mysql-pkey-creator-1.0-SNAPSHOT.jar help
~~~
### Running options
1. `help` - will show you the utility methods
2. `export` - will export the csv containing all table's which are missing primary keys
3. `exec` - will create primary keys, based on the inputted csv

