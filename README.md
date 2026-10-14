# Debugging Exercise

A maintenance assignment in an unfamiliar Java service. Work in the existing
structure and add regression tests for your changes.
No database, external service, authentication, or UI is needed.

## Setup

- JDK 21 and Maven 3.9 or newer.
- Open the project as a Maven project. Enable Lombok/annotation processing if your
  editor requires it; the Maven compiler is the build authority.

On macOS, select Java 21 if Maven uses another JDK:

```sh
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

```sh
mvn clean test
mvn spring-boot:run
```

## Existing API Contract

`POST /api/arrays/common-elements`

```json
{
  "left": [4, 5, 6, 12, 2, 6, 19, 1],
  "right": [7, 19, 3, 6]
}
```

Expected HTTP 200 response:

```json
{
  "commonElements": [6, 19]
}
```

- Return distinct values present in both arrays.
- Preserve first-occurrence order from `left`. Do not sort the result.
- Do not modify either input list.
- Both arrays are required. Empty arrays are valid and produce an empty result.
- Elements must be JSON integers in the signed 32-bit range. Negative numbers and
  zero are valid; null elements, numeric strings, and decimals are not.
- The current maximum is 10,000 elements per array.
- Missing/null arrays, invalid values, oversized arrays, malformed JSON, and
  unknown fields return HTTP 400. The error response body is not part of the task.

```sh
curl -i http://localhost:8080/api/arrays/common-elements \
  -H 'Content-Type: application/json' \
  -d '{"left":[4,5,6,12,2,6,19,1],"right":[7,19,3,6]}'
```

Set `PORT` to use another port, for example `PORT=8081 mvn spring-boot:run`.
The optional `requests.http` file contains additional requests.

## Tasks

### 1. Run the Application

Set up and run the application locally. Confirm that you can call the endpoint
using the sample request.

### 2. Investigate a Reported Issue

Users report that the API does not always return the expected results.
Investigate the behavior against the documented API contract, resolve the
underlying issue, and add regression coverage.

### 3. Extend the Behavior

Product request:

> Add an optional `matchMode` request field to the existing endpoint. `DISTINCT`
> returns unique common values, as today; `ALL` returns repeated common values
> when the inputs contain duplicates.
>
> Requests omitting `matchMode` must retain their current behavior. Increase the
> supported input size to 100,000 elements per array, keeping the same endpoint
> and response format.

## Layout

```text
src/main/java/com/example/interview/
  DebuggingApplication.java
  controller/DebuggingController.java
  bean/DebuggingRequest.java
  bean/DebuggingResponse.java
  service/DebuggingService.java
  service/impl/DebuggingServiceImpl.java
src/test/java/com/example/interview/
  controller/DebuggingControllerTest.java
  service/DebuggingServiceImplTest.java
```