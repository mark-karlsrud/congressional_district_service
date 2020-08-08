# Congressional District Location Service
Spring boot application for determining a US Congressional District based on location via a REST service.

## Stack
- Spring Boot
- Spring MVC
- Kotlin
- Gradle

## Setup
1. [Download the Congressional District Map shapefile](https://catalog.data.gov/dataset/tiger-line-shapefile-2018-nation-u-s-116th-congressional-district-national)

1. [Install Postgres](https://www.postgresql.org/download/)

1. [Install PostGIS](http://postgis.net/install/)

1. Create a database and add the postgis extension. [Need help?](http://postgis.net/workshops/postgis-intro/creating_db.html)

1. Load the shapefile into a new table in the database. [Need help?](https://geoserver.geo-solutions.it/educational/en/adding_data/shp_postgis.html)
    ```
    shp2pgsql -I /<path>/tl_2018_us_cd116.shp <your_table> | psql -d <database> -U postgres
    ```

1. Test to see if you can find your district. Example: Try with latitude `38.8899389` and longitude `-77.0112392` (the Capitol).
    ```
    SELECT * FROM <your_table> as district WHERE ST_CONTAINS(district.geom, ST_GeomFromText('Point(<longitude> <latitude>)'))
    ```

1. Checkout this project and customize `district_map.properties` accordingly.

1. Run this application
    ```
    ./gradlew bootRun
    ```

1. Test it out
    ```
    curl 'http://localhost:8080/district/map?longitude=-77.0112392&latitude=38.8899389'
    ```
