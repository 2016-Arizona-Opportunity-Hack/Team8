# WIREMOCK SERVER #


## Getting Started ##
To start call: `java -jar wiremock-standalone-2.2.1.jar` from the command line.

This will start a server on local host listening on port 8080.

To change port add `--port` with the desired port number.

For more options see wiremock documentation [here](http://wiremock.org/docs/running-standalone/)

## How to add new responses ##
Add new responses by creating a new response.json file in the mappings directory.
You can either look at and the files already there as an example or read the
wiremock documentation [here](http://wiremock.org/docs/stubbing/).
