# Traditional

This project demos what a traditional Java app using Camel might look like when using AWS Lambda.

## How to build and execution notes

1. Build: `mvn clean install`
2. Deploy to AWS: I did this manually, so roughly explained here are the steps:
    - Your target folder will have a file called `HelloWorld-1.0.jar`.
    - Create a new Java11 function on AWS (like through the web console).
    - Upload `HelloWorld-1.0.jar` as the code source for this new function.
    - Modify the config of the function so that the entrypoint is defined as `helloworld.App::handleRequest`.
3. You can now invoke the function. When I did, I saw:
```
{
  "statusCode": 200,
  "headers": {
    "X-Custom-Header": "application/json",
    "Content-Type": "application/json"
  },
  "body": "delectus aut autem"
}
```
I also want to call special attention to these extra metrics AWS handed to me:
* Init duration = 2328.84 ms
* Duration = 4337.31 ms
* Billed duration = 4338 ms
* Max memory used = 159 MB
* Memory allocated = 512 MB

Here are the stats when I decreased the memory allocation to 256 MB:
* Init duration = 2407.93 ms
* Duration = 8709.86 ms
* Billed duration = 8710 ms
* Max memory used = 163 MB
* Memory allocated = 256 MB
