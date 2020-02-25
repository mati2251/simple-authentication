# Simple Authentication System
This is simple project. The goal of project is learn and practice code in kotlin without framework.
## Start server
To start database start <br/>
``docker-compose up`` <br/>
Next you must insert to console database code from backup/backup.sql <br/>
Build jar by command <br/>
`gradle jar` <br/>
Jar file is in build/libs
## Config
You can config server by two file src/resources/mail.properties src/resources/server.properties <br/>
If you don't create file mail.properties Server will not work. <br/>
In file mail.properties you need props: mail, password, url, mail.smtp.host, mail.smtp.port, mail.smtp.auth, port, mail.smtp.starttls.enable and testMail
## Endpoints
`/login` 
with props <br/>
`mail` - user mail <br/>
`password` - user password <br/>
`/createUser` 
with props <br/>
`mail` - user mail <br/>
`password` - user password </br>
`name` - user name <br/>
`surname` - user surname <br/>
`isAdmin` - boolean check new user is admin
`/verifictaion` 
with props <br/>
`key` - verifictaion key </br>

## License 
GNU General Public License v3.0